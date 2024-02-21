package com.imss.sivimss.cpsf.configuration.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imss.sivimss.cpsf.model.request.Contratante;
import com.imss.sivimss.cpsf.model.request.Domicilio;
import com.imss.sivimss.cpsf.model.request.PagoSFPA;
import com.imss.sivimss.cpsf.model.request.PlanSFPA;
import com.imss.sivimss.cpsf.model.request.UsuarioDto;
import com.imss.sivimss.cpsf.model.response.ContratanteResponse;
import com.imss.sivimss.cpsf.model.response.DetallePlanSFPAResponse;
import com.imss.sivimss.cpsf.model.response.PagoSFPAResponse;
import com.imss.sivimss.cpsf.model.response.PersonaResponse;
import com.imss.sivimss.cpsf.model.response.ReportePagoAnticipadoReponse;

public interface PlanSFPAMapper {
	
	@Insert({"<script>", 
        "INSERT INTO SVC_PERSONA (CVE_RFC , CVE_CURP, CVE_NSS, NOM_PERSONA, NOM_PRIMER_APELLIDO, NOM_SEGUNDO_APELLIDO, NUM_SEXO, REF_OTRO_SEXO, FEC_NAC, ID_PAIS, ID_ESTADO,"
        + "REF_TELEFONO, REF_TELEFONO_FIJO, REF_CORREO, TIP_PERSONA, NUM_INE, ID_USUARIO_ALTA) VALUES ",
        "<foreach collection='personaList' item='persona' index='index' open='(' separator = '),(' close=')' >#{persona.rfc},#{persona.curp},#{persona.nss},#{persona.nomPersona},#{persona.primerApellido},#{persona.segundoApellido},#{persona.sexo}"
        + ",#{persona.otroSexo},#{persona.fecNacimiento},#{persona.idPais},#{persona.idEstado},#{persona.telefono},#{persona.telefonoFijo},#{persona.correo},#{persona.tipoPersona},#{persona.ine},#{persona.idUsuario}</foreach>",
        "</script>"})
    int insertPersonaList(@Param("personaList") List<Contratante> personaList);
	
	@Insert(value = "INSERT INTO SVC_PERSONA (CVE_RFC , CVE_CURP, CVE_NSS, NOM_PERSONA, NOM_PRIMER_APELLIDO, NOM_SEGUNDO_APELLIDO, NUM_SEXO, REF_OTRO_SEXO, FEC_NAC, ID_PAIS, ID_ESTADO, "
			+ "REF_TELEFONO, REF_TELEFONO_FIJO, REF_CORREO, TIP_PERSONA, NUM_INE, ID_USUARIO_ALTA)"
			+ "VALUES (#{persona.rfc},#{persona.curp},#{persona.nss},#{persona.nomPersona},#{persona.primerApellido},#{persona.segundoApellido},#{persona.sexo},#{persona.otroSexo},#{persona.fecNacimiento},#{persona.idPais},"
			+ "#{persona.idEstado},#{persona.telefono},#{persona.telefonoFijo},#{persona.correo},#{persona.tipoPersona},#{persona.ine},#{persona.idUsuario} )")
	@Options(useGeneratedKeys = true,keyProperty = "persona.idPersona", keyColumn="ID_PERSONA")
	public int insertarPersona(@Param("persona")Contratante persona);
	
	@Update(value = "UPDATE SVC_PERSONA SET CVE_RFC = #{persona.rfc}, CVE_CURP=#{persona.curp},  CVE_NSS=#{persona.nss}, NOM_PERSONA = #{persona.nomPersona}, NOM_PRIMER_APELLIDO = #{persona.primerApellido}, "
			+ " NOM_SEGUNDO_APELLIDO = #{persona.segundoApellido}, NUM_SEXO = #{persona.sexo}, REF_OTRO_SEXO = #{persona.otroSexo}, FEC_NAC = #{persona.fecNacimiento}, ID_PAIS=#{persona.idPais}, ID_ESTADO=#{persona.idEstado},"
			+ " REF_TELEFONO=#{persona.telefono}, REF_TELEFONO_FIJO=#{persona.telefonoFijo}, REF_CORREO=#{persona.correo}, TIP_PERSONA=#{persona.tipoPersona}, NUM_INE=#{persona.ine}, FEC_ACTUALIZACION=CURRENT_DATE(),"
			+ " ID_USUARIO_MODIFICA=#{persona.idUsuario}  WHERE ID_PERSONA = #{persona.idPersona}")
	public int actualizarPersona(@Param("persona")Contratante persona);
	
	@Insert(value = "INSERT INTO SVT_DOMICILIO (REF_CALLE,NUM_EXTERIOR,NUM_INTERIOR,REF_CP,REF_COLONIA,REF_MUNICIPIO,REF_ESTADO,ID_USUARIO_ALTA) "
			+ "VALUES (#{domicilio.desCalle},#{domicilio.numExterior},#{domicilio.numInterior},#{domicilio.codigoPostal},#{domicilio.desColonia},#{domicilio.desMunicipio},#{domicilio.desEstado},#{domicilio.idUsuario})")
	@Options(useGeneratedKeys = true,keyProperty = "domicilio.idDomicilio", keyColumn="ID_DOMICILIO")
	public int insertarDomicilio(@Param("domicilio")Domicilio domicilio);
	
	@Update(value = "UPDATE SVT_DOMICILIO SET REF_CALLE = #{domicilio.desCalle}, NUM_EXTERIOR=#{domicilio.numExterior},  NUM_INTERIOR=#{domicilio.numInterior}, REF_CP = #{domicilio.codigoPostal}, "
			+ " REF_COLONIA = #{domicilio.desColonia}, REF_MUNICIPIO = #{domicilio.desMunicipio}, REF_ESTADO = #{domicilio.desEstado}, FEC_ACTUALIZACION=CURRENT_DATE(), ID_USUARIO_MODIFICA=#{domicilio.idUsuario} "
			+ " WHERE ID_DOMICILIO = #{domicilio.idDomicilio}")
	public int actualizarDomicilio(@Param("domicilio")Domicilio domicilio);
	
	@Insert(value = "INSERT INTO SVC_CONTRATANTE (ID_PERSONA, CVE_MATRICULA, ID_DOMICILIO, ID_USUARIO_ALTA) "
			+ "VALUES (#{contratante.idPersona},#{contratante.matricula},#{contratante.cp.idDomicilio},#{contratante.idUsuario})")
	@Options(useGeneratedKeys = true,keyProperty = "contratante.idContratante", keyColumn="ID_CONTRATANTE")
	public int insertarContratante(@Param("contratante")Contratante Contratante);
	
	@Update(value = "UPDATE SVC_CONTRATANTE SET ID_PERSONA = #{contratante.idPersona}, CVE_MATRICULA=#{contratante.matricula}, ID_DOMICILIO = #{contratante.cp.idDomicilio}, "
			+ "  FEC_ACTUALIZACION=CURRENT_DATE(), ID_USUARIO_MODIFICA=#{contratante.idUsuario} WHERE ID_CONTRATANTE = #{contratante.idContratante}")
	public int actualizarContratante(@Param("contratante")Contratante Contratante);
	
	@Insert(value = "INSERT INTO SVT_TITULAR_BENEFICIARIOS (ID_PERSONA, CVE_MATRICULA, REF_PERSONA, ID_DOMICILIO, ID_USUARIO_ALTA) "
			+ "VALUES (#{titularBeneficiarios.idPersona},#{titularBeneficiarios.matricula},#{titularBeneficiarios.persona},#{titularBeneficiarios.cp.idDomicilio},#{titularBeneficiarios.idUsuario})")
	@Options(useGeneratedKeys = true,keyProperty = "titularBeneficiarios.idTitularBeneficiarios", keyColumn="ID_TITULAR_BENEFICIARIOS")
	public int insertarTitularBeneficiarios(@Param("titularBeneficiarios")Contratante Contratante);
	
	@Insert(value = "INSERT INTO SVT_TITULAR_BENEFICIARIOS (ID_PERSONA, CVE_MATRICULA, REF_PERSONA, ID_DOMICILIO, ID_USUARIO_ALTA) "
			+ "VALUES (#{titularBeneficiarios.idPersona},#{titularBeneficiarios.matricula},#{titularBeneficiarios.persona},#{titularBeneficiarios.cp.idDomicilio},#{titularBeneficiarios.idUsuario})")
	@Options(useGeneratedKeys = true,keyProperty = "titularBeneficiarios.idBeneficiario1", keyColumn="ID_TITULAR_BENEFICIARIOS")
	public int insertarTitularBeneficiario1(@Param("titularBeneficiarios")Contratante Contratante);
	
	@Insert(value = "INSERT INTO SVT_TITULAR_BENEFICIARIOS (ID_PERSONA, CVE_MATRICULA, REF_PERSONA, ID_DOMICILIO, ID_USUARIO_ALTA) "
			+ "VALUES (#{titularBeneficiarios.idPersona},#{titularBeneficiarios.matricula},#{titularBeneficiarios.persona},#{titularBeneficiarios.cp.idDomicilio},#{titularBeneficiarios.idUsuario})")
	@Options(useGeneratedKeys = true,keyProperty = "titularBeneficiarios.idBeneficiario2", keyColumn="ID_TITULAR_BENEFICIARIOS")
	public int insertarTitularBeneficiario2(@Param("titularBeneficiarios")Contratante Contratante);
	
	@Update(value = "UPDATE SVT_TITULAR_BENEFICIARIOS SET ID_PERSONA = #{titularBeneficiarios.idPersona}, CVE_MATRICULA=#{titularBeneficiarios.matricula},  REF_PERSONA=#{titularBeneficiarios.persona}, "
			+ " ID_DOMICILIO = #{titularBeneficiarios.cp.idDomicilio}, FEC_ACTUALIZACION=CURRENT_DATE(), ID_USUARIO_MODIFICA=#{titularBeneficiarios.idUsuario} WHERE ID_TITULAR_BENEFICIARIOS = #{titularBeneficiarios.idTitularBeneficiarios}")
	public int actualizarTitularBeneficiarios(@Param("titularBeneficiarios")Contratante Contratante);
	
	@Insert(value = "INSERT INTO SVT_USUARIOS (ID_PERSONA, ID_OFICINA, ID_ROL, IND_ACTIVO, CVE_CONTRASENIA, CVE_USUARIO , IND_CONTRATANTE, ID_USUARIO_ALTA) "
			+ "VALUES (#{usuario.idPersona},#{usuario.idOficina},#{usuario.idRol},#{usuario.indActivo},#{usuario.contrasenia},#{usuario.cveUsuario},#{usuario.indContratante},#{usuario.idUsuario})")
	@Options(useGeneratedKeys = true,keyProperty = "usuario.idUsuario", keyColumn="ID_USUARIO")
	public int insertarUsuario(@Param("usuario")UsuarioDto usuario);
	
	@Insert(value = "INSERT INTO SVT_PLAN_SFPA (NUM_FOLIO_PLAN_SFPA, ID_TIPO_CONTRATACION, ID_TITULAR, ID_PAQUETE, IMP_PRECIO, ID_TIPO_PAGO_MENSUAL, IND_TITULAR_SUBSTITUTO, IND_MODIF_TITULAR_SUB, "
			+ "ID_TITULAR_SUBSTITUTO, ID_BENEFICIARIO_1, ID_BENEFICIARIO_2, IND_PROMOTOR, ID_PROMOTOR, ID_VELATORIO, ID_ESTATUS_PLAN_SFPA, ID_USUARIO_ALTA) "
			+ "VALUES (#{plansfpa.numFolioPlanSfpa},#{plansfpa.idTipoContratacion},#{plansfpa.titular},#{plansfpa.idPaquete},#{plansfpa.monPrecio},#{plansfpa.idTipoPagoMensual},#{plansfpa.indTitularSubstituto},"
			+ "0,#{plansfpa.subtitular},#{plansfpa.beneficiario1},#{plansfpa.beneficiario2},#{plansfpa.indPromotor},#{plansfpa.idPromotor},#{plansfpa.idVelatorio},#{plansfpa.idEstatusPlanSfpa},#{plansfpa.idUsuario})")
	@Options(useGeneratedKeys = true,keyProperty = "plansfpa.idPlanSfpa", keyColumn="ID_PLAN_SFPA")
	public int insertarPlanSfpa(@Param("plansfpa")PlanSFPA planSFPARequest);
	
	@Insert(value = "INSERT INTO SVT_PAGO_SFPA (ID_PLAN_SFPA, IMP_MONTO_MENSUAL, FEC_PARCIALIDAD, ID_ESTATUS_PAGO, IND_ACTIVO, ID_USUARIO_ALTA) "
			+ "VALUES (#{pago.idPlanSfpa},#{pago.monMensual},DATE_ADD(CURDATE(), INTERVAL #{pago.i} MONTH),#{pago.idEstatusPagoSfpa},#{pago.indActivo},#{pago.idUsuario})")
	@Options(useGeneratedKeys = true,keyProperty = "pago.idPagoSfpa", keyColumn="ID_PAGO_SFPA")
	public int insertarPagosfpa(@Param("pago")PagoSFPA pagoSFPARequest);
	
	@Select("( SELECT CONCAT_WS('-',(SELECT SUBSTRING(UPPER(SV.DES_VELATORIO), 1, 3) FROM SVC_VELATORIO SV WHERE SV.ID_VELATORIO = #{idVelatorio} ),(SELECT SUBSTRING(UPPER(SP.REF_PAQUETE_NOMBRE), 1, 3) "
			+ "FROM SVT_PAQUETE SP WHERE SP.ID_PAQUETE = #{idPaquete} ),(SELECT STPM.DES_TIPO_PAGO_MENSUAL FROM SVC_TIPO_PAGO_MENSUAL STPM WHERE STPM.ID_TIPO_PAGO_MENSUAL = #{idTipoPagoMensual} ),"
			+ "(SELECT IFNULL(MAX(SPSFPA.ID_PLAN_SFPA), 0) + 1 FROM SVT_PLAN_SFPA SPSFPA )) AS NUM_FOLIO_PLAN_SFPA FROM DUAL )")
    String  selectnumFolioPlanSfpa(Integer idVelatorio, Integer idPaquete, Integer idTipoPagoMensual);
	
	@Select("SELECT PSFPA.IND_TITULAR_SUBSTITUTO indTitularSubs, DATE_FORMAT(P1.FEC_NAC, '%d-%m-%Y') as fecNacTitular, "
			+ "CONCAT(IFNULL(P1.NOM_PERSONA,''),' ',IFNULL(P1.NOM_PRIMER_APELLIDO,''), ' ',IFNULL(P1.NOM_SEGUNDO_APELLIDO,'')) AS nombreTitular   , SPIS.DES_PAIS AS nacionalidadTitular   , P1.CVE_RFC AS rfcTitular  , DO1.REF_CALLE AS calleTitular   , "
			+ "DO1.NUM_EXTERIOR AS numExterior   , DO1.NUM_INTERIOR AS numInterior  , DO1.REF_COLONIA AS colonia   , DO1.REF_CP AS codigoPostal  , DO1.REF_MUNICIPIO AS municipio   , DO1.REF_ESTADO AS estado  , P1.REF_CORREO AS correo  , P1.REF_TELEFONO AS telefono  , "
			+ "P1.REF_TELEFONO_FIJO AS telefonoFijo   , PQ.MON_PRECIO as totalImporte   , TPM.DES_TIPO_PAGO_MENSUAL AS numPago   , CONCAT(DO.REF_MUNICIPIO, ', ', DO.REF_ESTADO) AS ciudadFirma   , CONCAT(DATE_FORMAT(PSFPA.FEC_ALTA, '%d de '),ELT(MONTH(PSFPA.FEC_ALTA), "
			+ "\"ENERO\", \"FEBRERO\", \"MARZO\", \"ABRIL\", \"MAYO\", \"JUNIO\", \"JULIO\", \"AGOSTO\", \"SEPTIEMBRE\", \"OCTUBRE\", \"NOVIEMBRE\", \"DICIEMBRE\"),DATE_FORMAT(PSFPA.FEC_ALTA, ' de %Y')) AS fechaFirma  , PQ.REF_PAQUETE_NOMBRE AS nomPaquete   ,"
			+ "(SELECT GROUP_CONCAT(CONCAT( IFNULL(CONCAT('$',FORMAT(PAGO.IMP_MONTO_MENSUAL,2)) ,''),' - ',IFNULL(DATE_FORMAT(PAGO.FEC_PARCIALIDAD, '%d-%m-%Y' ),'') ) SEPARATOR '\\n') FROM SVT_PAGO_SFPA PAGO JOIN SVT_PLAN_SFPA PLAN ON PLAN.ID_PLAN_SFPA = PAGO.ID_PLAN_SFPA WHERE PAGO.ID_PLAN_SFPA  =  #{idPlanSfpa} ) AS pagos  , "
			+ "(SELECT GROUP_CONCAT(SO.DES_SERVICIO SEPARATOR  ', ') FROM SVT_SERVICIO SO JOIN SVC_DETALLE_CARAC_PAQ DCP ON DCP.ID_SERVICIO = SO.ID_SERVICIO JOIN SVC_CARACTERISTICAS_PAQUETE CPQ ON CPQ.ID_CARAC_PAQUETE = DCP.ID_DETALLE_CARAC JOIN SVT_PLAN_SFPA PSFPA ON PSFPA.ID_PAQUETE = CPQ.ID_PAQUETE "
			+ "WHERE PSFPA.ID_PLAN_SFPA  =  #{idPlanSfpa} ) AS servInclPaquete   , P5.REF_CORREO AS correoVelatorio   , CTE.ID_CONTRATANTE AS numeroAfiliacion   , CONCAT(IFNULL(P2.NOM_PERSONA,''),' ',IFNULL(P2.NOM_PRIMER_APELLIDO,''), ' ',IFNULL(P2.NOM_SEGUNDO_APELLIDO,'')) AS nombreSustituto  , DATE_FORMAT(P2.FEC_NAC, '%d-%m-%Y') AS fecNacSustituto  , "
			+ "P2.CVE_RFC AS rfcSustituto   , P2.REF_TELEFONO AS telefonoSustituto    , CONCAT(IFNULL(DO2.REF_CALLE,''),' ',IFNULL(DO2.NUM_EXTERIOR,''), ' ',IFNULL(DO2.NUM_INTERIOR,''), ' ',IFNULL(DO2.REF_COLONIA,''),' ',IFNULL(DO2.REF_MUNICIPIO,''), ' ',IFNULL(DO2.REF_ESTADO,''), ' ',IFNULL(DO2.REF_CP,'')) AS direccionSustituto   , "
			+ "CONCAT(IFNULL(P3.NOM_PERSONA,''),' ',IFNULL(P3.NOM_PRIMER_APELLIDO,''), ' ',IFNULL(P3.NOM_SEGUNDO_APELLIDO,'')) AS nombreB1  , DATE_FORMAT(P3.FEC_NAC, '%d-%m-%Y') AS fecNacB1   , P3.CVE_RFC AS rfcB1   , P3.REF_TELEFONO AS telefonoB1   , CONCAT(IFNULL(DO3.REF_CALLE,''),' ',IFNULL(DO3.NUM_EXTERIOR,''), ' ',"
			+ "IFNULL(DO3.NUM_INTERIOR,''), ' ',IFNULL(DO3.REF_CP,''),' ',IFNULL(DO3.REF_COLONIA,''),' ',IFNULL(DO3.REF_MUNICIPIO,''), ' ',IFNULL(DO3.REF_ESTADO,'')) AS direccionB1  , CONCAT(IFNULL(P4.NOM_PERSONA,''),' ',IFNULL(P4.NOM_PRIMER_APELLIDO,''), ' ',IFNULL(P4.NOM_SEGUNDO_APELLIDO,'')) AS nombreB2   , DATE_FORMAT(P4.FEC_NAC, '%d-%m-%Y') AS fecNacB2   , "
			+ "P4.CVE_RFC AS rfcB2   , P4.REF_TELEFONO AS telefonoB2   , CONCAT(IFNULL(DO4.REF_CALLE,''),' ',IFNULL(DO4.NUM_EXTERIOR,''), ' ',IFNULL(DO4.NUM_INTERIOR,''),' ',IFNULL(DO4.REF_CP,''), ' ',IFNULL(DO4.REF_COLONIA,''),' ',IFNULL(DO4.REF_MUNICIPIO,''), ' ',IFNULL(DO4.REF_ESTADO,'')) AS direccionB2   FROM SVT_PLAN_SFPA PSFPA    "
			+ "JOIN SVT_PAQUETE PQ ON PQ.ID_PAQUETE = PSFPA.ID_PAQUETE and PQ.IND_ACTIVO = 1   JOIN SVC_CONTRATANTE CTE ON CTE.ID_CONTRATANTE = PSFPA.ID_TITULAR  JOIN SVC_PERSONA P1 ON P1.ID_PERSONA = CTE.ID_PERSONA   JOIN SVC_PAIS SPIS ON P1.ID_PAIS = SPIS.ID_PAIS  JOIN SVT_DOMICILIO DO1 ON CTE.ID_DOMICILIO = DO1.ID_DOMICILIO   "
			+ "JOIN SVC_TIPO_PAGO_MENSUAL TPM  on TPM.ID_TIPO_PAGO_MENSUAL = PSFPA.ID_TIPO_PAGO_MENSUAL   JOIN SVC_VELATORIO VO ON VO.ID_VELATORIO = PSFPA.ID_VELATORIO  JOIN SVT_USUARIOS US ON US.ID_USUARIO = VO.ID_USUARIO_ADMIN  LEFT JOIN  SVC_PERSONA P5 ON P5.ID_PERSONA  = US.ID_PERSONA  JOIN SVT_DOMICILIO DO "
			+ "ON DO.ID_DOMICILIO = CTE.ID_DOMICILIO  LEFT JOIN SVT_TITULAR_BENEFICIARIOS TBSP1 ON TBSP1.ID_TITULAR_BENEFICIARIOS  = PSFPA.ID_TITULAR_SUBSTITUTO  LEFT JOIN SVC_PERSONA P2 ON P2.ID_PERSONA = TBSP1.ID_PERSONA  LEFT JOIN SVT_DOMICILIO DO2 ON DO2.ID_DOMICILIO = TBSP1.ID_DOMICILIO  LEFT JOIN "
			+ "SVT_TITULAR_BENEFICIARIOS TBSP2 ON TBSP2.ID_TITULAR_BENEFICIARIOS  = PSFPA.ID_BENEFICIARIO_1  LEFT JOIN SVC_PERSONA P3 ON P3.ID_PERSONA = TBSP2.ID_PERSONA  LEFT JOIN SVT_DOMICILIO DO3 ON DO3.ID_DOMICILIO = TBSP2.ID_DOMICILIO  LEFT JOIN SVT_TITULAR_BENEFICIARIOS TBSP3 ON TBSP3.ID_TITULAR_BENEFICIARIOS  = PSFPA.ID_BENEFICIARIO_2  "
			+ "LEFT JOIN SVC_PERSONA P4 ON P4.ID_PERSONA = TBSP3.ID_PERSONA  LEFT JOIN SVT_DOMICILIO DO4 ON DO4.ID_DOMICILIO = TBSP3.ID_DOMICILIO WHERE PSFPA.ID_PLAN_SFPA = #{idPlanSfpa}")
	public ReportePagoAnticipadoReponse generaReporte(Integer idPlanSfpa);
	
	
	@Select("SELECT SPSFPA.ID_PLAN_SFPA as idPlanSfpa, SPSFPA.NUM_FOLIO_PLAN_SFPA as numFolioPlanSfpa, SPSFPA.ID_TIPO_CONTRATACION as idTipoContratacion, SPSFPA.ID_PAQUETE as idPaquete, paq.REF_PAQUETE_NOMBRE as nomPaquete, "
			+ "paq.REF_PAQUETE_DESCRIPCION  as desPaquete, SPSFPA.ID_TIPO_PAGO_MENSUAL as idTipoPagoMensual,  STPM.DES_TIPO_PAGO_MENSUAL as desTipoPagoMensual, SPSFPA.IND_TITULAR_SUBSTITUTO as indTitularSubstituto, "
			+ "SPSFPA.ID_ESTATUS_PLAN_SFPA as idEstatusPlanSfpa, SEPS.DES_ESTATUS_PLAN_SFPA as desEstatusPlanSfpa, SPSFPA.IND_MODIF_TITULAR_SUB as indModificarTitularSubstituto, SPSFPA.ID_PROMOTOR as idPromotor, SPSFPA.IND_PROMOTOR as indPromotor, SPSFPA.IND_ACTIVO as indActivo, SPSFPA.ID_VELATORIO as idVelatorio, SV.DES_VELATORIO as desIdVelatorio, SP.ID_PERSONA as idPersona,  "
			+ "SP.CVE_RFC as rfc, SP.CVE_CURP as curp, SC.CVE_MATRICULA as matricula, SP.CVE_NSS as nss, SP.NOM_PERSONA as nomPersona, SP.NOM_PRIMER_APELLIDO as primerApellido, SP.NOM_SEGUNDO_APELLIDO as segundoApellido, "
			+ "CASE WHEN SP.NUM_SEXO= 1 THEN 'MUJER' WHEN SP.NUM_SEXO= 2 THEN 'HOMBRE' ELSE 'OTRO' END as sexo, "
			+ "DATE_FORMAT(SP.FEC_NAC, '%d-%m-%Y') as fecNacimiento, SP.REF_OTRO_SEXO as otroSexo, SP.ID_PAIS as idPais, "
			 +"SP.ID_ESTADO as idEstado, pais.DES_PAIS pais, edo.DES_ESTADO lugarNac, IF(SP.ID_PAIS=119, 'MEXICANA', 'EXTRANJERA') AS nacionalidad, "
			+ "SP.REF_TELEFONO as telefono, SP.REF_TELEFONO_FIJO as telefonoFijo, SP.REF_CORREO as correo, SP.TIP_PERSONA as tipoPersona, SP.NUM_INE as ine, SD.ID_DOMICILIO as idDomicilio, SD.REF_CALLE as desCalle, SD.NUM_EXTERIOR as numExterior, SD.NUM_INTERIOR as numInterior, "
			+ "SD.REF_CP as codigoPostal, SD.REF_COLONIA as desColonia, SD.REF_MUNICIPIO as desMunicipio, SD.REF_ESTADO as desEstado, SV.DES_VELATORIO as desVelatorio, SP2.ID_PERSONA as idPersona2, SP2.CVE_RFC as rfc2, SP2.CVE_CURP as curp2, STB2.CVE_MATRICULA as matricula2, SP2.CVE_NSS as nss2, "
			+ "SP2.NOM_PERSONA as nomPersona2, SP2.NOM_PRIMER_APELLIDO as primerApellido2, SP2.NOM_SEGUNDO_APELLIDO as segundoApellido2, "
			+ "CASE WHEN SP2.NUM_SEXO=1 THEN 'MUJER' WHEN SP2.NUM_SEXO=2 THEN 'HOMBRE' ELSE 'OTRO' END sexo2, DATE_FORMAT(SP2.FEC_NAC, '%d-%m-%Y') as fecNacimiento2, SP2.REF_OTRO_SEXO as otroSexo2, SP2.ID_PAIS as idPais2, SP2.ID_ESTADO as idEstado2, "
			 +"pais2.DES_PAIS pais2, edo2.DES_ESTADO lugarNac2, IF(SP2.ID_PAIS=119, 'MEXICANA', 'EXTRANJERA') AS nacionalidad2, SP2.REF_TELEFONO as telefono2, "
			+ "SP2.REF_TELEFONO_FIJO as telefonoFijo2, SP2.REF_CORREO as correo2, SP2.TIP_PERSONA as tipoPersona2, SP2.NUM_INE as ine2, SD2.ID_DOMICILIO as idDomicilio2, SD2.REF_CALLE as desCalle2, SD2.NUM_EXTERIOR as numExterior2, SD2.NUM_INTERIOR as numInterior2, SD2.REF_CP as codigoPostal2, "
			+ "SD2.REF_COLONIA as desColonia2, SD2.REF_MUNICIPIO as desMunicipio2, SD2.REF_ESTADO as desEstado2, DATE_FORMAT(SPSFPA.FEC_INGRESO, '%d/%m/%Y') as fecIngreso, ( SELECT COUNT(*) FROM SVT_PAGO_SFPA PSFPA INNER JOIN SVT_PLAN_SFPA PLSFPA on PSFPA.ID_PLAN_SFPA = PLSFPA.ID_PLAN_SFPA AND "
			+ "PSFPA.ID_ESTATUS_PAGO = 4 AND PLSFPA.ID_ESTATUS_PLAN_SFPA not in (6) AND PLSFPA.IND_ACTIVO = 1 WHERE PSFPA.ID_PLAN_SFPA = #{idPlanSfpa} AND PSFPA.IND_ACTIVO = 1) as numPago, SP3.ID_PERSONA as idPersona3, SP3.CVE_RFC as rfc3, SP3.CVE_CURP as curp3, STB3.CVE_MATRICULA as matricula3, "
			+ "SP3.CVE_NSS as nss3, SP3.NOM_PERSONA as nomPersona3, SP3.NOM_PRIMER_APELLIDO as primerApellido3, SP3.NOM_SEGUNDO_APELLIDO as segundoApellido3, "
			+ "CASE WHEN SP3.NUM_SEXO= 1 THEN 'MUJER' WHEN SP3.NUM_SEXO= 2 THEN 'HOMBRE' ELSE 'OTRO' END as sexo3, DATE_FORMAT(SP3.FEC_NAC, '%d-%m-%Y') fecNacimiento3, SP3.REF_OTRO_SEXO as otroSexo3, SP3.ID_PAIS as idPais3, SP3.ID_ESTADO as idEstado3, "
			+"pais3.DES_PAIS as pais3, edo3.DES_ESTADO as lugarNac3,IF(SP3.ID_PAIS=119, 'MEXICANA', 'EXTRANJERA') AS nacionalidad3, SP3.REF_TELEFONO as telefono3, SP3.REF_TELEFONO_FIJO as telefonoFijo3, SP3.REF_CORREO as correo3, SP3.TIP_PERSONA as tipoPersona3, SP3.NUM_INE as ine3, SD3.ID_DOMICILIO as idDomicilio3, SD3.REF_CALLE as desCalle3, SD3.NUM_EXTERIOR as numExterior3, SD3.NUM_INTERIOR as numInterior3, "
			+ "SD3.REF_CP as codigoPostal3, SD3.REF_COLONIA as desColonia3, SD3.REF_MUNICIPIO as desMunicipio3, SD3.REF_ESTADO as desEstado3, SP4.ID_PERSONA as idPersona4, SP4.CVE_RFC as rfc4, SP4.CVE_CURP as curp4, STB4.CVE_MATRICULA as matricula4, SP4.CVE_NSS as nss4, SP4.NOM_PERSONA as nomPersona4, "
			+ "SP4.NOM_PRIMER_APELLIDO as primerApellido4, SP4.NOM_SEGUNDO_APELLIDO as segundoApellido4, "
			+" CASE WHEN SP4.NUM_SEXO= 1 THEN 'MUJER' WHEN SP4.NUM_SEXO= 2 THEN 'HOMBRE' ELSE 'OTRO' END as sexo4, DATE_FORMAT(SP4.FEC_NAC, '%d-%m-%Y') fecNacimiento4, SP4.REF_OTRO_SEXO as otroSexo4, SP4.ID_PAIS as idPais4, SP4.ID_ESTADO as idEstado4, "
			+ "pais4.DES_PAIS as pais4, edo4.DES_ESTADO as lugarNac4, IF(SP4.ID_PAIS=119, 'MEXICANA', 'EXTRANJERA') AS nacionalidad4, SP4.REF_TELEFONO as telefono4, SP4.REF_TELEFONO_FIJO as telefonoFijo4, "
			+ "SP4.REF_CORREO as correo4, SP4.TIP_PERSONA as tipoPersona4, SP4.NUM_INE as ine4, SD4.ID_DOMICILIO as idDomicilio4, SD4.REF_CALLE as desCalle4, SD4.NUM_EXTERIOR as numExterior4, SD4.NUM_INTERIOR as numInterior4, SD4.REF_CP as codigoPostal4, SD4.REF_COLONIA as desColonia4, SD4.REF_MUNICIPIO as desMunicipio4, "
			+ "SD4.REF_ESTADO as desEstado4 FROM SVT_PLAN_SFPA SPSFPA INNER JOIN SVC_CONTRATANTE SC on SC.ID_CONTRATANTE = SPSFPA.ID_TITULAR "
			+ "INNER JOIN SVC_PERSONA SP on SP.ID_PERSONA = SC.ID_PERSONA LEFT JOIN SVC_PAIS pais ON SP.ID_PAIS = pais.ID_PAIS LEFT JOIN SVC_ESTADO edo ON SP.ID_ESTADO = edo.ID_ESTADO "
			+ "INNER JOIN SVT_DOMICILIO SD on SD.ID_DOMICILIO = SC.ID_DOMICILIO LEFT JOIN SVT_TITULAR_BENEFICIARIOS STB2 "
			+ "on STB2.ID_TITULAR_BENEFICIARIOS = SPSFPA.ID_TITULAR_SUBSTITUTO LEFT JOIN SVC_PERSONA SP2 on SP2.ID_PERSONA = STB2.ID_PERSONA "
			+"LEFT JOIN SVC_PAIS pais2 ON SP2.ID_PAIS = pais2.ID_PAIS LEFT JOIN SVC_ESTADO edo2 ON SP2.ID_ESTADO = edo2.ID_ESTADO LEFT JOIN SVT_DOMICILIO SD2 on SD2.ID_DOMICILIO = STB2.ID_DOMICILIO LEFT JOIN SVT_TITULAR_BENEFICIARIOS STB3 on STB3.ID_TITULAR_BENEFICIARIOS = SPSFPA.ID_BENEFICIARIO_1 "
			+ "LEFT JOIN SVC_PERSONA SP3 on SP3.ID_PERSONA = STB3.ID_PERSONA "
			+ "LEFT JOIN SVC_PAIS pais3 ON SP3.ID_PAIS = pais3.ID_PAIS LEFT JOIN SVC_ESTADO edo3 ON SP3.ID_ESTADO = edo3.ID_ESTADO LEFT JOIN SVT_DOMICILIO SD3 on SD3.ID_DOMICILIO = STB3.ID_DOMICILIO LEFT JOIN SVT_TITULAR_BENEFICIARIOS STB4 on STB4.ID_TITULAR_BENEFICIARIOS = SPSFPA.ID_BENEFICIARIO_2 LEFT JOIN SVC_PERSONA SP4 on SP4.ID_PERSONA = STB4.ID_PERSONA "
			+ "LEFT JOIN SVC_PAIS pais4 ON SP4.ID_PAIS = pais4.ID_PAIS LEFT JOIN SVC_ESTADO edo4 ON SP4.ID_ESTADO = edo4.ID_ESTADO LEFT JOIN SVT_DOMICILIO SD4 on SD4.ID_DOMICILIO = STB4.ID_DOMICILIO INNER JOIN SVC_VELATORIO SV on SV.ID_VELATORIO = SPSFPA.ID_VELATORIO INNER JOIN SVC_TIPO_PAGO_MENSUAL STPM ON STPM.ID_TIPO_PAGO_MENSUAL  = SPSFPA.ID_TIPO_PAGO_MENSUAL INNER JOIN SVC_ESTATUS_PLAN_SFPA SEPS ON "
			+ "SEPS.ID_ESTATUS_PLAN_SFPA  = SPSFPA.ID_ESTATUS_PLAN_SFPA INNER JOIN SVT_PAQUETE paq on SPSFPA.ID_PAQUETE = paq.ID_PAQUETE WHERE SPSFPA.ID_PLAN_SFPA = #{idPlanSfpa} AND SPSFPA.IND_ACTIVO = 1")
	public DetallePlanSFPAResponse selectVerdetallePlanSFPA(Integer idPlanSfpa);
	
	@Select("SELECT pg.idPagoSFPA,pg.idEstatus, CONCAT(CAST((@ROW := @ROW + 1) AS VARCHAR(255)),'/', ( " +
            " SELECT COUNT(pf.ID_PAGO_SFPA) " +
            " FROM SVT_PAGO_SFPA pf " +
            " WHERE pf.IND_ACTIVO = 1 AND pf.ID_PLAN_SFPA = pg.idPlanSFPA)) AS noPagos,  " +
            " pg.idPlanSFPA, pg.velatorio, DATE_FORMAT(pg.fechaParcialidad,'%d/%m/%Y') AS fechaParcialidad,  " +
            " pg.importeMensual, pg.estatusPago, pg.importePagado,  " +
            " CASE WHEN pg.importePagado < pg.importeMensual && pg.fechaParcialidad = CURDATE() THEN TRUE  " +
            " WHEN pg.importePagado = pg.importeMensual THEN FALSE " +
            " ELSE FALSE END AS validaPago,  " +
            " pg.importePagado , pg.importeMensual , " +
            " pg.fechaParcialidad , " +
            " CASE WHEN pg.idEstatus = 2 THEN 0 WHEN MONTH(pg.fechaParcialidad) = MONTH(CURDATE()) && ((pg.importeFaltante + pg.importeMensual) - pg.importePagadoBitacora) > 0  "
            +
            " THEN (pg.importeFaltante + pg.importeMensual) - pg.importePagadoBitacora " +
            " when pg.importeMensual - pg.importePagado  > 0 " +
            " then  pg.importeMensual - pg.importePagado " +
            " ELSE pg.importeMensual END AS importeAcumulado, pg.folioRecibo " +
            " FROM ( " +
            " SELECT ps.ID_PAGO_SFPA AS idPagoSFPA, ps.ID_PLAN_SFPA AS idPlanSFPA,ps.ID_ESTATUS_PAGO AS idEstatus, v.DES_VELATORIO AS velatorio, ps.FEC_PARCIALIDAD AS fechaParcialidad, ps.IMP_MONTO_MENSUAL AS importeMensual, ep.DES_ESTATUS_PAGO_ANTICIPADO AS estatusPago, ( "
            +
            " SELECT " +
            " (IFNULL(SUM(bpa.IMP_PAGO),0) + IFNULL(SUM(bpa.IMP_AUTORIZADO_VALE_PARITARIO),0)) " +
            " FROM SVC_BITACORA_PAGO_ANTICIPADO bpa " +
            " WHERE bpa.IND_ACTIVO = 1 AND bpa.ID_PAGO_SFPA = ps.ID_PAGO_SFPA) AS importePagado, ps.IND_ACTIVO, ( "
            +
            " SELECT IFNULL(SUM(sps.IMP_MONTO_MENSUAL),0) " +
            " FROM SVT_PAGO_SFPA sps" +
            " WHERE sps.ID_ESTATUS_PAGO = 2 AND sps.IND_ACTIVO = 1 AND sps.FEC_PARCIALIDAD <= CURDATE() AND sps.ID_PLAN_SFPA = ps.ID_PLAN_SFPA) AS importeFaltante, ( "
            +
            " SELECT IFNULL(SUM(bpaa.IMP_PAGO),0)  + IFNULL(SUM(bpaa.IMP_AUTORIZADO_VALE_PARITARIO),0) " +
            " FROM SVT_PAGO_SFPA sps" +
            " JOIN SVC_BITACORA_PAGO_ANTICIPADO bpaa ON bpaa.ID_PAGO_SFPA= sps.ID_PAGO_SFPA " +
            " AND bpaa.IND_ACTIVO = 1  " +
            " WHERE sps.IND_ACTIVO = 1 AND sps.ID_PLAN_SFPA = ps.ID_PLAN_SFPA) AS importePagadoBitacora, ps.IMP_MONTO_MENSUAL, "
            +
            " IFNULL(ps.REF_FOLIO_RECIBO,'') as folioRecibo " +
            " FROM SVT_PAGO_SFPA ps " +
            " JOIN SVT_PLAN_SFPA pls ON pls.ID_PLAN_SFPA = ps.ID_PLAN_SFPA " +
            " JOIN SVC_VELATORIO v ON v.ID_VELATORIO = pls.ID_VELATORIO " +
            " JOIN SVC_ESTATUS_PAGO_ANTICIPADO ep ON ep.ID_ESTATUS_PAGO_ANTICIPADO = ps.ID_ESTATUS_PAGO) AS pg, ( "
            +
            " SELECT @ROW := 0) r " +
            " WHERE pg.idPlanSFPA = #{idPlanSfpa} AND pg.IND_ACTIVO = 1")
	public List<PagoSFPAResponse> selectVerdetallePagoSFPA(Integer idPlanSfpa);
	
	@Select("SELECT TIP_PARAMETRO AS imgCheck FROM SVC_PARAMETRO_SISTEMA sps WHERE sps.ID_FUNCIONALIDAD = 25 AND sps.DES_PARAMETRO = 'IMAGEN_CHECK'")
	public String getImagenCheck();
	
	@Select("SELECT TIP_PARAMETRO AS firmDir FROM SVC_PARAMETRO_SISTEMA sps WHERE sps.ID_FUNCIONALIDAD = 25 AND sps.DES_PARAMETRO = 'FIRMA_DIRECTORA'")
	public String getImagenFirma();
	
	@Select("SELECT TIP_PARAMETRO AS nomFibeso FROM SVC_PARAMETRO_SISTEMA sps WHERE sps.ID_FUNCIONALIDAD = 20 AND sps.DES_PARAMETRO = 'NOMBRE FIBESO'")
	public String getNomFibeso();
	
	@Select("SELECT SPSFPA.ID_TITULAR as idTitular, SP.ID_PERSONA AS idPersona, IFNULL(SP.NOM_PERSONA, '') AS nomPersona, IFNULL(SP.NOM_PRIMER_APELLIDO, '') AS nomApellidoPaterno, "
			+ "IFNULL(SP.NOM_SEGUNDO_APELLIDO, '') AS nomApellidoMaterno, IFNULL(SP.REF_CORREO , '') AS correo FROM SVT_PLAN_SFPA SPSFPA  INNER JOIN SVC_CONTRATANTE SC ON "
			+ "SC.ID_CONTRATANTE = SPSFPA.ID_TITULAR  INNER JOIN SVC_PERSONA SP ON SP.ID_PERSONA = SC.ID_PERSONA  WHERE IFNULL(SPSFPA.ID_PLAN_SFPA ,0) > 0  AND SPSFPA.ID_PLAN_SFPA = #{idPlanSfpa}")
	public ContratanteResponse selectContratante(Integer idPlanSfpa);
	
	@Select("SELECT SC.ID_CONTRATANTE AS idContratante, SD.ID_DOMICILIO AS idDomicilio, SP.ID_PERSONA AS idPersona FROM SVC_PERSONA SP  LEFT JOIN SVC_CONTRATANTE SC ON SP.ID_PERSONA = SC.ID_PERSONA  "
			+ " LEFT JOIN SVT_DOMICILIO SD ON SD.ID_DOMICILIO = SC.ID_DOMICILIO  WHERE IFNULL(SP.ID_PERSONA , 0) > 0  ${where}")
	public PersonaResponse selectExisteContratante(@Param("where")String where);
	
	@Select("SELECT STB.ID_TITULAR_BENEFICIARIOS AS idTitularBeneficiarios, SD.ID_DOMICILIO AS idDomicilio, SP.ID_PERSONA AS idPersona FROM SVC_PERSONA SP  LEFT JOIN SVT_TITULAR_BENEFICIARIOS STB ON SP.ID_PERSONA = STB.ID_PERSONA  "
			+ " LEFT JOIN SVT_DOMICILIO SD ON SD.ID_DOMICILIO = STB.ID_DOMICILIO  WHERE IFNULL(SP.ID_PERSONA , 0) > 0  ${where}")
	public PersonaResponse selectExisteTitularBeneficiarios(@Param("where")String where);


}
