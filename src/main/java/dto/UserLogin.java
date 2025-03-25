
package dto;

/**
 *
 * @author Daniel Mora Cantillo
 */
public record UserLogin(
        String _token, 
        String user, 
        String password) {}
