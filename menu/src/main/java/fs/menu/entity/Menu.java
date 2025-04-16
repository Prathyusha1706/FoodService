package fs.menu.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer menuId;
    private String menuType;
    @Transient
    private List<Item> items;
}