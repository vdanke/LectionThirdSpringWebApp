package org.step.model;

import javax.persistence.*;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "full_name", length = 250)
    private String fullName;
    @Column(name = "username", length = 120, unique = true)
    private String username;
    @Column(name = "password", length = 120)
    private String password;

    @OneToOne(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    private Profile profile;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    // FetchType EAGER только на oneToOne

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Like> likeList = new ArrayList<>();

    @ManyToMany(mappedBy = "userList")
    private List<Course> courseList = new ArrayList<>();
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private List<Rating> ratingList = new ArrayList<>();

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

    public User(String fullName, String username, String password) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }

    public User(Integer id, String fullName, String username, String password) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
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
