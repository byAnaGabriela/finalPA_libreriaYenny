package util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

// Con esta clase valido formatos que se repiten en diferentes clases service
public class Validaciones {

    // Defino los patrones o reglas de validación como expresiones regulares(regex) estáticos y compilados una sola vez
    // Esto para usarlos a la hora de validar textos, mails y variables String que deben ser únicamente números

    private static final Pattern PATRON_SOLO_TEXTO = Pattern.compile("^[\\p{L} ]+$");
    /* - ^ y $: inicio y final de la cadena
       - \\p{L}: cualquier letra de cualquier idioma (incluyendo tíldes y ñ)
       - Se incluye un espacio para permitir espacios entre las palabras
       - +: uno o más caracteres
       - VALIDO -> Ana Medina
       - INVALIDO -> Ana123 o Ana@
    */

    private static final Pattern PATRON_SOLO_NUMEROS = Pattern.compile("^[0-9]+$");
    /* - ^ y $: inicio y final de la cadena
       - [0-9]: permite un rango del 0 al 9
       - +: uno o más caracteres
       - VALIDO -> 12345
       - INVALIDO -> 123-45
    */

    private static final Pattern PATRON_MAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");
    /* - ^[\w.+-]+: antes del @, permíte letras, números guiones bajos(\\w), puntos(.), signos(+-). Debe tener al menos 1 de esos caracteres
       - @: es obligatorio y separa el nombre de usuario del dominio
       - [\w-]+: dominio(por ej: gmail) permíte letras, números y guiones
       - \\.: Obliga a que haya un punto para separar el dominio de la extensión
       - [a-zA-Z]{2,}$: extensión final(por ej: com) obliga a que sean solo letras y que como mínimo tenga 2 caracteres
       - VALIDO -> ana.medina@gmail.com
       - INVALIDO -> ana@@gmail.com / ana@com
    */

    //Constructor privado pq no se instancia esta clase, todos los métodos son de acceso global
    private Validaciones() {}

    // Todos los métodos son static pq no guardo nada, solo evalúan el dato y devuelven true o false

    // ★゜・。。・゜゜・。。・゜☆ Validaciones de textos y vacíos ☆゜・。。・゜゜・。。・゜★

    public static boolean esTextoValido(String texto) {
        // Verifico que el texto no sea nulo, que no esté compuesto únicamente por espacios y que cumpla con el patrón de solo letras(y acentos + espacios) designado arriba
        return texto != null && !texto.trim().isEmpty() && PATRON_SOLO_TEXTO.matcher(texto).matches();
        // .matcher(texto) toma la regla establecida en PATRON_SOLO_TEXTO y la prepara para buscar coincidencias en la variable
        // .matches() es el metodo final que comprueba si el texto encaja con lo establecido, si es válido devuelve true y si no, devuelve false
    }

    public static boolean esCampoVacio(String texto) {
        // Verifico si el texto es nulo o si está vacío o solo con espacios
        return texto == null || texto.trim().isEmpty();
    }

    // ★゜・。。・゜゜・。。・゜☆ Validaciones numéricas y de identificación ☆゜・。。・゜゜・。。・゜★

    public static boolean esSoloNumeros(String texto) {
        // Verifico que el texto no sea nulo y que contenga solo números (0-9)
        return texto != null && PATRON_SOLO_NUMEROS.matcher(texto).matches();
    }

    public static boolean esDniValido(String dni) {
        // Verifico que el dni sean solo números y que la cantidad este dentro del rango establecido
        return esSoloNumeros(dni) && dni.length() >= 7 && dni.length() <= 9;
    }

    public static boolean esCelularValido(String celular) {
        // Verifico que el celular sean solo números y que la cantidad este dentro del rango establecido
        return esSoloNumeros(celular) && celular.length() >= 8 && celular.length() <= 15;
    }

    public static boolean esMailValido(String mail) {
        // Verifico que el mail no sea nulo y que su estructura coincida con la expresión regular establecida
        return mail != null && PATRON_MAIL.matcher(mail).matches();
    }

    // ★゜・。。・゜゜・。。・゜☆ Validaciones de contraseñas ☆゜・。。・゜゜・。。・゜★

    // Reglas: Mínimo 8 caracteres | Al menos una letra | Al menos un número
    public static boolean esContrasenaSegura(String contrasena) {
        // Verifico que la contraseña no sea nula y que sea mayor/igual a 8
        if (contrasena == null || contrasena.length() < 8) {
            return false;
        }

        boolean tieneLetra = false;
        boolean tieneNumero = false;
        for (int i = 0; i < contrasena.length(); i++) {
            char caracterActual = contrasena.charAt(i);

            if (Character.isLetter(caracterActual)) {
                tieneLetra = true;
            }
            if (Character.isDigit(caracterActual)) {
                tieneNumero = true;
            }
        }
        // La contraseña es segura si tiene al menos una letra y un número (además de los 8 dígitos)
        return tieneLetra && tieneNumero;
    }

    // ★゜・。。・゜゜・。。・゜☆ Validaciones de precios y cantidades ☆゜・。。・゜゜・。。・゜★

    public static boolean esPrecioValido(BigDecimal precio) {
        // Verifico que el precio no sea nulo y que sea mayor a 0
        return precio != null && precio.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean esCantidadValida(int cantidad) {
        // Verifico que la cantidad sea mayor/igual a 0
        return cantidad >= 0;
    }

    public static boolean esCantidadMayorACero(int cantidad) {
        // Verifico que la cantidad sea mayor a 0
        return cantidad > 0;
    }

    // ★゜・。。・゜゜・。。・゜☆ Validaciones de fechas ☆゜・。。・゜゜・。。・゜★

    public static boolean esFechaPasadaOActual(LocalDate fecha) {
        // Verifico que la fecha no sea nula y que sea pasada o actual (NO futura)
        return fecha != null && !fecha.isAfter(LocalDate.now());
    }

    public static boolean esFechaPasadaOActual(LocalDateTime fecha) {
        // Verifico que la fecha y hora no sea nula y que sea pasada o actual (NO futura)
        return fecha != null && !fecha.isAfter(LocalDateTime.now());
    }

    // ★゜・。。・゜゜・。。・゜☆ Validaciones de codigo ISBN(identificador de versiones de libros) ☆゜・。。・゜゜・。。・゜★

    public static boolean esIsbnValido(String isbn) {
        // Si el ISBN es nulo lo rechazo
        if (isbn == null)  {
            return false;
        }
        // Como los ISBN se suelen escribir con guiones, los elimino para quedarme solo con los números
        String soloNumeros = isbn.replace("-", "");

        // Verifico que el ISBN tenga solo números y que la cantidad este dentro del rango establecido
        return esSoloNumeros(soloNumeros) && soloNumeros.length() >= 10 && soloNumeros.length() <= 13;
    }

}
