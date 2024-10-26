package business;

import lombok.*;

/**
 * @author Filip Vojtěch
 */
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class User {
    private int id;
    private @NonNull String email;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final @NonNull String passwordHash;
    private String displayName;
}
