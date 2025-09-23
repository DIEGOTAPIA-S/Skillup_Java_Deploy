public interface CurriculumSeccionRepository extends JpaRepository<CurriculumSeccion, Long> {
    List<CurriculumSeccion> findByUsuarioId(Long usuarioId);
    Optional<CurriculumSeccion> findByUsuarioIdAndTitulo(Long usuarioId, String titulo);
}