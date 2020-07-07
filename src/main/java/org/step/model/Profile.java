package org.step.model;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    @Column(name = "skill", length = 60)
    private String skill;
    @Column(name = "description", length = 1024)
    private String description;
    @Column(name = "count_profiles", columnDefinition = "bigserial")
    @Generated(GenerationTime.INSERT)
    private long countProfiles;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_profile_fk"))
    private User user;

    public Profile() {
    }

    public Profile(String skill, String description) {
        this.skill = skill;
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCountProfiles() {
        return countProfiles;
    }

    public void setCountProfiles(long countProfiles) {
        this.countProfiles = countProfiles;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", skill='" + skill + '\'' +
                ", description='" + description + '\'' +
                ", countProfiles=" + countProfiles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return countProfiles == profile.countProfiles &&
                Objects.equals(id, profile.id) &&
                Objects.equals(skill, profile.skill) &&
                Objects.equals(description, profile.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, skill, description, countProfiles);
    }
}
