package com.amigoscode.devicemanagement.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@ToString
public class User implements Serializable {

    String id;
    String email;
    String name;
    String password;
    Set<UserRole> roles;
    ZonedDateTime createdAt;
    ZonedDateTime deletedAt;
    ZonedDateTime updatedAt;
    String updatedBy;

    public User withPassword(String newPassword) {
        return new User(
                id,
                email,
                name,
                newPassword,
                roles,
                createdAt,
                deletedAt,
                updatedAt,
                updatedBy);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final User user = (User) o;

        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(name, user.name)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (!Objects.equals(roles, user.roles)) return false;
        if (!isItTheSameDate(createdAt, user.createdAt)) return false;
        if (!isItTheSameDate(updatedAt, user.updatedAt)) return false;
        if (!isItTheSameDate(deletedAt, user.deletedAt)) return false;
        return Objects.equals(updatedBy, user.updatedBy);
    }

    private boolean isItTheSameDate(final ZonedDateTime date1, final ZonedDateTime date2) {
        if(date1 == null && date2 == null) return true;
        if(date1 != null && date2 == null) return false;
        if(date1 == null && date2 != null) return false;
        return Objects.equals(date1.toInstant(), date2.toInstant());
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (deletedAt != null ? deletedAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (updatedBy != null ? updatedBy.hashCode() : 0);
        return result;
    }
}