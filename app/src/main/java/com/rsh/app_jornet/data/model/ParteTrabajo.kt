data class ParteTrabajo(
    val id: String = "",
    val fecha: String = "",
    val obra: String = "",
    val maquinas: List<Map<String, String>> = emptyList(),
    val trabajosRealizados: String = "",
    val turno: String = "",
    val tipoJornada: String = "",
    val observaciones: String = "",
    val empleados: List<Map<String, String>> = emptyList(),
    val horasTrabajadas: Int = 0,
    val horasExtras: Int = 0,
    val dieta: String = "",
    val uid: String = "",
    val archivado: Boolean = false
)
