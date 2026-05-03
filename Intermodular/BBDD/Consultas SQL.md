### Calcular el total de horas trabajadas por cada empleado, mostrando solo los fichajes completados.

SELECT 
    u.nombre,
    u.apellidos,
    COUNT(f.id_fichaje) AS dias_trabajados,
    SEC_TO_TIME(SUM(TIME_TO_SEC(TIMEDIFF(f.horaSalida, f.horaEntrada)))) AS horas_totales
FROM usuarios u
JOIN fichaje f ON u.id_usuario = f.id_usuario
WHERE f.estado = 'COMPLETADO'
  AND MONTH(f.fechaFichaje) = 4
  AND YEAR(f.fechaFichaje) = 2025
GROUP BY u.id_usuario, u.nombre, u.apellidos
ORDER BY horas_totales DESC;

### Mostrar quién tiene fichajes sin cerrar
SELECT 
    u.nombre,
    u.apellidos,
    u.email,
    f.fechaFichaje,
    f.horaEntrada
FROM usuarios u
JOIN fichaje f ON u.id_usuario = f.id_usuario
WHERE f.estado = 'PENDIENTE'
  AND f.horaSalida IS NULL
ORDER BY f.fechaFichaje DESC;


### Muestra cuántos días disponibles le quedan a cada empleado, con el desglose de solicitados y estado.
SELECT 
    u.nombre,
    u.apellidos,
    u.tipoJornada,
    v.diasDisponibles,
    COALESCE(SUM(v.diasSolicitados), 0) AS dias_solicitados,
    (v.diasDisponibles - COALESCE(SUM(v.diasSolicitados), 0)) AS dias_restantes,
    v.estado
FROM usuarios u
JOIN vacaciones v ON u.id_usuario = v.id_usuario
WHERE YEAR(v.fechaInicio) = YEAR(CURDATE())
GROUP BY u.id_usuario, u.nombre, u.apellidos, u.tipoJornada, v.diasDisponibles, v.estado
ORDER BY u.apellidos;

### Muestra proyectos activos con su responsable y número de empleados asignados
SELECT 
    p.nombre AS proyecto,
    p.cliente,
    p.fechaInicio,
    CONCAT(u.nombre, ' ', u.apellidos) AS responsable,
    COUNT(pu.id_usuario) AS empleados_asignados
FROM proyecto p
JOIN usuarios u ON p.id_responsable = u.id_usuario
LEFT JOIN proyecto_usuarios pu ON p.id_proyecto = pu.id_proyecto
WHERE p.fechaFin IS NULL OR p.fechaFin >= CURDATE()
GROUP BY p.id_proyecto, p.nombre, p.cliente, p.fechaInicio, responsable
ORDER BY p.fechaInicio DESC;