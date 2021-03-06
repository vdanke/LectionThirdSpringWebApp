package org.step.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}, name = "username_user_uk")},
        indexes = {@Index(columnList = "username", unique = true)}
)

@NamedEntityGraph(
        name = "user.comments",
        attributeNodes = {
                @NamedAttributeNode(value = "commentList"),
                @NamedAttributeNode(value = "profile")
//                        subgraph = "comment.like")
        }
//        subgraphs = {
//                @NamedSubgraph(
//                        name = "comment.like",
//                        type = Like.class,
//                        attributeNodes = @NamedAttributeNode(
//                                value = "user"
//                        )
//                )
//        }
)
//@NamedQuery(name = "User.findByUsername",
//    query = "select u from User u where u.username = ?1")
@EntityListeners(AuditingEntityListener.class)
public class User extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Size(min = 5, max = 250, message = "Full name is not valid")
    @NotBlank(message = "Full name cannot be empty")
    @Column(name = "full_name", length = 250)
    private String fullName;
    @Column(name = "password", length = 120)
    private String password;
    private Integer age;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (age == null || age.equals(0)) {
            age = 99;
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        if (updatedAt.isBefore(LocalDateTime.now())) {
            updatedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @JsonIgnore
    @OneToOne(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    private Profile profile;

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    // FetchType EAGER только на oneToOne

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Like> likeList = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "userList")
    private List<Course> courseList = new ArrayList<>();
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private List<Rating> ratingList = new ArrayList<>();

    @JsonIgnore
    @CollectionTable(
            name = "authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            foreignKey = @ForeignKey(name = "user_authorities_fk")
    )
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> authorities = new HashSet<>();

    public User() {
    }

    public User(String fullName, String username, String password, Integer age) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public User(Integer id, String fullName, String username, String password) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    //    public List<Rating> getRatingList() {
//        return ratingList;
//    }
//
//    public void setRatingList(List<Rating> ratingList) {
//        this.ratingList = ratingList;
//    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Like> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<Like> likeList) {
        this.likeList = likeList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(fullName, user.fullName) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, username, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
