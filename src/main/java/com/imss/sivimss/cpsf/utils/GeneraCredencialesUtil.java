package com.imss.sivimss.cpsf.utils;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.imss.sivimss.cpsf.configuration.mapper.PlanSFPAMapper;
import com.imss.sivimss.cpsf.model.request.CorreoRequest;
import com.imss.sivimss.cpsf.model.request.UsuarioDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GeneraCredencialesUtil{

	@Autowired
	PasswordEncoder passwordEncoder;
	
	 @Autowired
	private ProviderServiceRestTemplate providerRestTemplate;
	
	@Value("${endpoints.envio-correo}")
	private String urlEnvioCorreo;
	
	public String insertarUser(Integer numberUser, String nombreCompleto, String paterno, String contrasenia, Integer idPersona, Integer idUsuario, Integer idVelatorio, PlanSFPAMapper planSFPAMapper){
		String hash = passwordEncoder.encode(contrasenia);
		String[] obtieneNombre = nombreCompleto.split(" ");
        String nombre = obtieneNombre[0];
        char[] apellido= paterno.toCharArray();
        char apM = apellido[0];
        String inicialApellido = String.valueOf(apM);
        String formatearCeros = String.format("%03d", numberUser);
		String user = nombre+inicialApellido+formatearCeros;
		UsuarioDto usuario = new UsuarioDto ();
		usuario.setIdPersona(idPersona);
		usuario.setContrasenia(hash);
		usuario.setCveUsuario(user);
		usuario.setIdOficina(3);
		usuario.setIdRol(150);
		usuario.setIdVelatorio(idVelatorio);
		usuario.setIndActivo(1);
		usuario.setIndContratante(1);
		usuario.setIdUsuario(idUsuario);
		planSFPAMapper.insertarUsuario(usuario);
		return user;
	}

	public String generarContrasenia(String nombreCompleto, String paterno) {
		SecureRandom random = new SecureRandom();
        String caracteres = "#$^+=!*()@%&";
        int randomInt =random.nextInt(caracteres.length());
        String[] obtieneNombre = nombreCompleto.toLowerCase().split(" ");
        String nombre = obtieneNombre[0];
        for(int i=1; nombre.length()>i; i++  ) {
        	if(nombre.charAt(i)==nombre.charAt(i-1)) {
        		char letra = nombre.charAt(i);
        		char caracter = caracteres.charAt(randomInt);
        		nombre =nombre.replaceFirst(String.valueOf(letra), "?");
        	    nombre=nombre.replace(letra, caracter);
                nombre = nombre.replace("?", String.valueOf(letra));
            caracteres = caracteres.replace(String.valueOf(caracter), "");
        	}
        }
      
        char randomChar = caracteres.charAt(randomInt);
        char[] apellido= paterno.toLowerCase().toCharArray();
        
        char pLetra = apellido[0];//paterno 0
        String pLetraS = String.valueOf(pLetra); 
        char sLetra= apellido[1];
        if(pLetra==sLetra) { //SEGUNDA LETRA PATERNO
        	sLetra = caracteres.charAt(randomInt);
        }
        SimpleDateFormat fecActual = new SimpleDateFormat("MM");
		String numMes = fecActual.format(new Date());
		
		return nombre+randomChar+"."+pLetraS.toUpperCase()+sLetra+numMes;
	} 

	public Response<Object> enviarCorreo(String user, String correo, String nombre, String paterno, String materno,
			String contrasenia) throws IOException {
		log.info("envioCorreo " + urlEnvioCorreo);
		String credenciales = "<b>Nombre completo del Usuario:</b> " + nombre + " " + paterno + " " + materno
				+ "<br> <b>Clave de usuario: </b>" + user + "<br> <b>Contraseña: </b>" + contrasenia;
		CorreoRequest correoR = new CorreoRequest(user, credenciales, correo, AppConstantes.USR_CONTRASENIA);
		// Hacemos el consumo para enviar el codigo por correo

		Response<Object> response = providerRestTemplate.consumirServicio(correoR, urlEnvioCorreo);

		if (response.getCodigo() != 200) {
			return new Response<>(true, 500, "Error en el envio de correos", null);
		}
		return response;
	}
	
	
	
}
