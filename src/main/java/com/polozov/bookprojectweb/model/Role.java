package com.polozov.bookprojectweb.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum Role {
    USER(Set.of(Permission.BOOK_READ)),
    CLIENT(Set.of(
            Permission.BOOK_READ,
            Permission.AUTHOR_READ,
            Permission.GENRE_READ,
            Permission.COMMENT_READ)),
    ADMIN(Set.of(
            Permission.BOOK_READ,
            Permission.BOOK_WRITE,
            Permission.AUTHOR_READ,
            Permission.AUTHOR_WRITE,
            Permission.GENRE_READ,
            Permission.GENRE_WRITE,
            Permission.COMMENT_READ)),;

    private final Set<Permission> permissions;

    /**
     * Связываем permission с SimpleGrantedAuthority
     * @return множество разрешений конкретной роли
     */
    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
