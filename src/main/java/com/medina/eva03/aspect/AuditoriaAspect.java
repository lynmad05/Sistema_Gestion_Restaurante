package com.medina.eva03.aspect;

import com.medina.eva03.model.Bitacora;
import com.medina.eva03.model.Cliente; // Importación necesaria para getObjectId
import com.medina.eva03.model.Mesa;    // Importación necesaria para getObjectId
import com.medina.eva03.repository.BitacoraRepository;
import com.medina.eva03.repository.UsuarioRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditoriaAspect {

    @Autowired
    private BitacoraRepository bitacoraRepository;

    // INYECCIÓN NECESARIA para buscar el ID del usuario logeado
    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1. Pointcut base: Operaciones CRUD en la mayoría de los repositorios.
    @Pointcut("execution(* com.medina.eva03.repository.*.save(..)) || " +
            "execution(* com.medina.eva03.repository.*.deleteById(..))")
    public void allRepositoryCrudOperations() {}

    // 2. Pointcut de exclusión: Operaciones en el BitacoraRepository.
    @Pointcut("execution(* com.medina.eva03.repository.BitacoraRepository.*(..))")
    public void bitacoraOperations() {}

    /**
     * CONSEJO PRINCIPAL: Aplica AOP a CRUD, PERO NO a Bitacora para evitar el StackOverflowError.
     * Captura la información después de que la operación ha sido exitosa.
     */
    @AfterReturning(pointcut = "allRepositoryCrudOperations() && !bitacoraOperations()")
    public void registrarAuditoria(JoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        String accion = "";
        String tablaAfectada = "";
        String detalle = "";

        // --- Lógica para determinar el tipo de acción (CREAR/ACTUALIZAR/ELIMINAR) ---
        if (methodName.equals("save")) {
            Object entity = args[0];
            tablaAfectada = entity.getClass().getSimpleName();

            if (getObjectId(entity) != null) {
                accion = "ACTUALIZAR";
                detalle = tablaAfectada + " con ID " + getObjectId(entity) + " actualizado.";
            } else {
                accion = "CREAR";
                // Nota: Aquí el ID será null porque se creará después de esta interceptación,
                // pero ya está guardado en la base de datos.
                detalle = tablaAfectada + " creado.";
            }
        } else if (methodName.equals("deleteById")) {
            Integer id = (Integer) args[0];
            String repositoryName = joinPoint.getTarget().getClass().getSimpleName();
            tablaAfectada = repositoryName.replace("Repository", "");

            accion = "ELIMINAR";
            detalle = tablaAfectada + " con ID " + id + " eliminado.";
        }

        // --- Construcción y Registro en Bitácora ---
        if (!accion.isEmpty()) {
            Bitacora bitacora = new Bitacora();
            bitacora.setAccion(accion);
            bitacora.setTablaAfectada(tablaAfectada);
            bitacora.setDetalle(detalle);
            bitacora.setFechaHora(LocalDateTime.now());

            // Lógica para CAPTURAR EL ID DEL USUARIO (Vinculación con Spring Security)
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
                String nombreUsuario = auth.getName();

                // Busca el ID del usuario autenticado en la base de datos
                usuarioRepository.findByNombreUsuario(nombreUsuario).ifPresent(usuario -> {
                    // Si encontramos el usuario, guardamos su ID
                    bitacora.setIdUsuario(usuario.getIdUsuario());
                });
            }

            bitacoraRepository.save(bitacora);
        }
    }

    /**
     * Método utilitario para obtener el ID de la entidad dinámicamente.
     */
    private Integer getObjectId(Object entity) {
        if (entity instanceof Cliente) {
            return ((Cliente) entity).getIdCliente();
        } else if (entity instanceof Mesa) {
            return ((Mesa) entity).getIdMesa();
        }
        // Puedes agregar más entidades aquí (Usuario, Producto, etc.)
        return null;
    }
}