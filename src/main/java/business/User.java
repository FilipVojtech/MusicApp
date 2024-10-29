package business;

import lombok.*;

/**
 * @author Filip Vojtěch
 */
@AllArgsConstructor
@Data
@Builder
public class User {
    private int id;
    private @NonNull String email;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final String password;
    private @NonNull String displayName;
}
