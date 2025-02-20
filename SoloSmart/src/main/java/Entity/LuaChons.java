package Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Embeddable
@Data
@NoArgsConstructor
@Table(name = "dsLuaChon")
public class LuaChons {
    @Column(name="luaChon", nullable = false, columnDefinition = "nvarchar(255)")
    private String luaChon;

    @Column(name="dapAnDung", nullable = false)
    private boolean dapAnDung;
}
