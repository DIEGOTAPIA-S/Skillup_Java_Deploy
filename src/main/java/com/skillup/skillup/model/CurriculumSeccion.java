@Entity
public class CurriculumSeccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;   // o relación con entidad Usuario
    private String titulo;    // "Perfil", "Experiencia laboral", etc.
    
    @Column(columnDefinition = "TEXT")
    private String contenido;

    // getters y setters
}
