
package dto;

/**
 *
 * @author Daniel Mora Cantillo
 */
public record UserLoginDTO(
        String _token, 
        String user, 
        String password) {}
