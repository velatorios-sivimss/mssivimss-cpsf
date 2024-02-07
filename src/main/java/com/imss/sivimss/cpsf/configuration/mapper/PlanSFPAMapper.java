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
	
	@Select("SELECT  CONCAT(IFNULL(P1.NOM_PERSONA,''),' ',IFNULL(P1.NOM_PRIMER_APELLIDO,''), ' ',IFNULL(P1.NOM_SEGUNDO_APELLIDO,'')) AS nombreTitular   , SPIS.DES_PAIS AS nacionalidadTitular   , P1.CVE_RFC AS rfcTitular  , DO1.REF_CALLE AS calleTitular   , "
			+ "DO1.NUM_EXTERIOR AS numExterior   , DO1.NUM_INTERIOR AS numInterior  , DO1.REF_COLONIA AS colonia   , DO1.REF_CP AS codigoPostal  , DO1.REF_MUNICIPIO AS municipio   , DO1.REF_ESTADO AS estado  , P1.REF_CORREO AS correo  , P1.REF_TELEFONO AS telefono  , "
			+ "P1.REF_TELEFONO_FIJO AS telefonoFijo   , PQ.MON_PRECIO as totalImporte   , TPM.DES_TIPO_PAGO_MENSUAL AS numPago   , CONCAT(DO.REF_MUNICIPIO, ', ', DO.REF_ESTADO) AS ciudadFirma   , CONCAT(DATE_FORMAT(PSFPA.FEC_ALTA, '%d de '),ELT(MONTH(PSFPA.FEC_ALTA), "
			+ "\"ENERO\", \"FEBRERO\", \"MARZO\", \"ABRIL\", \"MAYO\", \"JUNIO\", \"JULIO\", \"AGOSTO\", \"SEPTIEMBRE\", \"OCTUBRE\", \"NOVIEMBRE\", \"DICIEMBRE\"),DATE_FORMAT(PSFPA.FEC_ALTA, ' de %Y')) AS fechaFirma  , PQ.REF_PAQUETE_NOMBRE AS nomPaquete   ,"
			+ "(SELECT GROUP_CONCAT(CONCAT( IFNULL(PAGO.IMP_MONTO_MENSUAL ,''),' - ',IFNULL(PAGO.FEC_PARCIALIDAD,'') ) SEPARATOR '\\n') FROM SVT_PAGO_SFPA PAGO JOIN SVT_PLAN_SFPA PLAN ON PLAN.ID_PLAN_SFPA = PAGO.ID_PLAN_SFPA WHERE PAGO.ID_PLAN_SFPA  =  #{idPlanSfpa} ) AS pagos  , "
			+ "(SELECT GROUP_CONCAT(SO.DES_SERVICIO SEPARATOR  ', ') FROM SVT_SERVICIO SO JOIN SVC_DETALLE_CARAC_PAQ DCP ON DCP.ID_SERVICIO = SO.ID_SERVICIO JOIN SVC_CARACTERISTICAS_PAQUETE CPQ ON CPQ.ID_CARAC_PAQUETE = DCP.ID_DETALLE_CARAC JOIN SVT_PLAN_SFPA PSFPA ON PSFPA.ID_PAQUETE = CPQ.ID_PAQUETE "
			+ "WHERE PSFPA.ID_PLAN_SFPA  =  #{idPlanSfpa} ) AS servInclPaquete   , P5.REF_CORREO AS correoVelatorio   , CTE.ID_CONTRATANTE AS numeroAfiliacion   , CONCAT(IFNULL(P2.NOM_PERSONA,''),' ',IFNULL(P2.NOM_PRIMER_APELLIDO,''), ' ',IFNULL(P2.NOM_SEGUNDO_APELLIDO,'')) AS nombreSustituto  , P2.FEC_NAC AS fecNacSustituto  , "
			+ "P2.CVE_RFC AS rfcSustituto   , P2.REF_TELEFONO AS telefonoSustituto    , CONCAT(IFNULL(DO2.REF_CALLE,''),' ',IFNULL(DO2.NUM_EXTERIOR,''), ' ',IFNULL(DO2.NUM_INTERIOR,''), ' ',IFNULL(DO2.REF_COLONIA,''),' ',IFNULL(DO2.REF_MUNICIPIO,''), ' ',IFNULL(DO2.REF_ESTADO,''), ' ',IFNULL(DO2.REF_CP,'')) AS direccionSustituto   , "
			+ "CONCAT(IFNULL(P3.NOM_PERSONA,''),' ',IFNULL(P3.NOM_PRIMER_APELLIDO,''), ' ',IFNULL(P3.NOM_SEGUNDO_APELLIDO,'')) AS nombreB1  , P3.FEC_NAC AS fecNacB1   , P3.CVE_RFC AS rfcB1   , P3.REF_TELEFONO AS telefonoB1   , CONCAT(IFNULL(DO3.REF_CALLE,''),' ',IFNULL(DO3.NUM_EXTERIOR,''), ' ',"
			+ "IFNULL(DO3.NUM_INTERIOR,''), ' ',IFNULL(DO3.REF_COLONIA,''),' ',IFNULL(DO3.REF_MUNICIPIO,''), ' ',IFNULL(DO3.REF_ESTADO,''), ' ',IFNULL(DO3.REF_CP,'')) AS direccionB1  , CONCAT(IFNULL(P4.NOM_PERSONA,''),' ',IFNULL(P4.NOM_PRIMER_APELLIDO,''), ' ',IFNULL(P4.NOM_SEGUNDO_APELLIDO,'')) AS nombreB2   , P4.FEC_NAC AS fecNacB2   , "
			+ "P4.CVE_RFC AS rfcB2   , P4.REF_TELEFONO AS telefonoB2   , CONCAT(IFNULL(DO4.REF_CALLE,''),' ',IFNULL(DO4.NUM_EXTERIOR,''), ' ',IFNULL(DO4.NUM_INTERIOR,''), ' ',IFNULL(DO4.REF_COLONIA,''),' ',IFNULL(DO4.REF_MUNICIPIO,''), ' ',IFNULL(DO4.REF_ESTADO,''), ' ',IFNULL(DO4.REF_CP,'')) AS direccionB2   FROM SVT_PLAN_SFPA PSFPA    "
			+ "JOIN SVT_PAQUETE PQ ON PQ.ID_PAQUETE = PSFPA.ID_PAQUETE and PQ.IND_ACTIVO = 1   JOIN SVC_CONTRATANTE CTE ON CTE.ID_CONTRATANTE = PSFPA.ID_TITULAR  JOIN SVC_PERSONA P1 ON P1.ID_PERSONA = CTE.ID_PERSONA   JOIN SVC_PAIS SPIS ON P1.ID_PAIS = SPIS.ID_PAIS  JOIN SVT_DOMICILIO DO1 ON CTE.ID_DOMICILIO = DO1.ID_DOMICILIO   "
			+ "JOIN SVC_TIPO_PAGO_MENSUAL TPM  on TPM.ID_TIPO_PAGO_MENSUAL = PSFPA.ID_TIPO_PAGO_MENSUAL   JOIN SVC_VELATORIO VO ON VO.ID_VELATORIO = PSFPA.ID_VELATORIO  JOIN SVT_USUARIOS US ON US.ID_USUARIO = VO.ID_USUARIO_ADMIN  LEFT JOIN  SVC_PERSONA P5 ON P5.ID_PERSONA  = US.ID_PERSONA  JOIN SVT_DOMICILIO DO "
			+ "ON DO.ID_DOMICILIO = CTE.ID_DOMICILIO  LEFT JOIN SVT_TITULAR_BENEFICIARIOS TBSP1 ON TBSP1.ID_TITULAR_BENEFICIARIOS  = PSFPA.ID_TITULAR_SUBSTITUTO  LEFT JOIN SVC_PERSONA P2 ON P2.ID_PERSONA = TBSP1.ID_PERSONA  LEFT JOIN SVT_DOMICILIO DO2 ON DO2.ID_DOMICILIO = TBSP1.ID_DOMICILIO  LEFT JOIN "
			+ "SVT_TITULAR_BENEFICIARIOS TBSP2 ON TBSP2.ID_TITULAR_BENEFICIARIOS  = PSFPA.ID_BENEFICIARIO_1  LEFT JOIN SVC_PERSONA P3 ON P3.ID_PERSONA = TBSP2.ID_PERSONA  LEFT JOIN SVT_DOMICILIO DO3 ON DO3.ID_DOMICILIO = TBSP2.ID_DOMICILIO  LEFT JOIN SVT_TITULAR_BENEFICIARIOS TBSP3 ON TBSP3.ID_TITULAR_BENEFICIARIOS  = PSFPA.ID_BENEFICIARIO_2  "
			+ "LEFT JOIN SVC_PERSONA P4 ON P4.ID_PERSONA = TBSP3.ID_PERSONA  LEFT JOIN SVT_DOMICILIO DO4 ON DO4.ID_DOMICILIO = TBSP3.ID_DOMICILIO WHERE PSFPA.ID_PLAN_SFPA = #{idPlanSfpa}")
	public ReportePagoAnticipadoReponse generaReporte(Integer idPlanSfpa);
	
	
	@Select("SELECT SPSFPA.ID_PLAN_SFPA as idPlanSfpa, SPSFPA.NUM_FOLIO_PLAN_SFPA as numFolioPlanSfpa, SPSFPA.ID_TIPO_CONTRATACION as idTipoContratacion, SPSFPA.ID_PAQUETE as idPaquete, SPSFPA.ID_TIPO_PAGO_MENSUAL as idTipoPagoMensual,  STPM.DES_TIPO_PAGO_MENSUAL as desTipoPagoMensual, SPSFPA.IND_TITULAR_SUBSTITUTO as indTitularSubstituto, "
			+ "SPSFPA.ID_ESTATUS_PLAN_SFPA as idEstatusPlanSfpa, SEPS.DES_ESTATUS_PLAN_SFPA as desEstatusPlanSfpa, SPSFPA.IND_MODIF_TITULAR_SUB as indModificarTitularSubstituto, SPSFPA.ID_PROMOTOR as idPromotor, SPSFPA.IND_PROMOTOR as indPromotor, SPSFPA.IND_ACTIVO as indActivo, SPSFPA.ID_VELATORIO as idVelatorio, SV.DES_VELATORIO as desIdVelatorio, SP.ID_PERSONA as idPersona,  "
			+ "SP.CVE_RFC as rfc, SP.CVE_CURP as curp, SC.CVE_MATRICULA as matricula, SP.CVE_NSS as nss, SP.NOM_PERSONA as nomPersona, SP.NOM_PRIMER_APELLIDO as primerApellido, SP.NOM_SEGUNDO_APELLIDO as segundoApellido, SP.NUM_SEXO as sexo, SP.REF_OTRO_SEXO as otroSexo, SP.FEC_NAC as fecNacimiento, SP.ID_PAIS as idPais, "
			+ "SP.ID_ESTADO as idEstado, SP.REF_TELEFONO as telefono, SP.REF_TELEFONO_FIJO as telefonoFijo, SP.REF_CORREO as correo, SP.TIP_PERSONA as tipoPersona, SP.NUM_INE as ine, SD.ID_DOMICILIO as idDomicilio, SD.REF_CALLE as desCalle, SD.NUM_EXTERIOR as numExterior, SD.NUM_INTERIOR as numInterior, "
			+ "SD.REF_CP as codigoPostal, SD.REF_COLONIA as desColonia, SD.REF_MUNICIPIO as desMunicipio, SD.REF_ESTADO as desEstado, SV.DES_VELATORIO as desVelatorio, SP2.ID_PERSONA as idPersona2, SP2.CVE_RFC as rfc2, SP2.CVE_CURP as curp2, STB2.CVE_MATRICULA as matricula2, SP2.CVE_NSS as nss2, "
			+ "SP2.NOM_PERSONA as nomPersona2, SP2.NOM_PRIMER_APELLIDO as primerApellido2, SP2.NOM_SEGUNDO_APELLIDO as segundoApellido2, SP2.NUM_SEXO as sexo2, SP2.REF_OTRO_SEXO as otroSexo2, SP2.FEC_NAC as fecNacimiento2, SP2.ID_PAIS as idPais2, SP2.ID_ESTADO as idEstado2, SP2.REF_TELEFONO as telefono2, "
			+ "SP2.REF_TELEFONO_FIJO as telefonoFijo2, SP2.REF_CORREO as correo2, SP2.TIP_PERSONA as tipoPersona2, SP2.NUM_INE as ine2, SD2.ID_DOMICILIO as idDomicilio2, SD2.REF_CALLE as desCalle2, SD2.NUM_EXTERIOR as numExterior2, SD2.NUM_INTERIOR as numInterior2, SD2.REF_CP as codigoPostal2, "
			+ "SD2.REF_COLONIA as desColonia2, SD2.REF_MUNICIPIO as desMunicipio2, SD2.REF_ESTADO as desEstado2, DATE_FORMAT(SPSFPA.FEC_INGRESO, '%d/%m/%Y') as fecIngreso, ( SELECT COUNT(*) FROM SVT_PAGO_SFPA PSFPA INNER JOIN SVT_PLAN_SFPA PLSFPA on PSFPA.ID_PLAN_SFPA = PLSFPA.ID_PLAN_SFPA AND "
			+ "PSFPA.ID_ESTATUS_PAGO = 4 AND PLSFPA.ID_ESTATUS_PLAN_SFPA not in (6) AND PLSFPA.IND_ACTIVO = 1 WHERE PSFPA.ID_PLAN_SFPA = #{idPlanSfpa} AND PSFPA.IND_ACTIVO = 1) as numPago, SP3.ID_PERSONA as idPersona3, SP3.CVE_RFC as rfc3, SP3.CVE_CURP as curp3, STB3.CVE_MATRICULA as matricula3, "
			+ "SP3.CVE_NSS as nss3, SP3.NOM_PERSONA as nomPersona3, SP3.NOM_PRIMER_APELLIDO as primerApellido3, SP3.NOM_SEGUNDO_APELLIDO as segundoApellido3, SP3.NUM_SEXO as sexo3, SP3.REF_OTRO_SEXO as otroSexo3, SP3.FEC_NAC as fecNacimiento3, SP3.ID_PAIS as idPais3, SP3.ID_ESTADO as idEstado3, "
			+ "SP3.REF_TELEFONO as telefono3, SP3.REF_TELEFONO_FIJO as telefonoFijo3, SP3.REF_CORREO as correo3, SP3.TIP_PERSONA as tipoPersona3, SP3.NUM_INE as ine3, SD3.ID_DOMICILIO as idDomicilio3, SD3.REF_CALLE as desCalle3, SD3.NUM_EXTERIOR as numExterior3, SD3.NUM_INTERIOR as numInterior3, "
			+ "SD3.REF_CP as codigoPostal3, SD3.REF_COLONIA as desColonia3, SD3.REF_MUNICIPIO as desMunicipio3, SD3.REF_ESTADO as desEstado3, SP4.ID_PERSONA as idPersona4, SP4.CVE_RFC as rfc4, SP4.CVE_CURP as curp4, STB4.CVE_MATRICULA as matricula4, SP4.CVE_NSS as nss4, SP4.NOM_PERSONA as nomPersona, "
			+ "SP4.NOM_PRIMER_APELLIDO as primerApellido4, SP4.NOM_SEGUNDO_APELLIDO as segundoApellido4, SP4.NUM_SEXO as sexo4, SP4.REF_OTRO_SEXO as otroSexo4, SP4.FEC_NAC as fecNacimiento4, SP4.ID_PAIS as idPais4, SP4.ID_ESTADO as idEstado4, SP4.REF_TELEFONO as telefono4, SP4.REF_TELEFONO_FIJO as telefonoFijo4, "
			+ "SP4.REF_CORREO as correo4, SP4.TIP_PERSONA as tipoPersona4, SP4.NUM_INE as ine4, SD4.ID_DOMICILIO as idDomicilio4, SD4.REF_CALLE as desCalle4, SD4.NUM_EXTERIOR as numExterior4, SD4.NUM_INTERIOR as numInterior4, SD4.REF_CP as codigoPostal4, SD4.REF_COLONIA as desColonia4, SD4.REF_MUNICIPIO as desMunicipio4, "
			+ "SD4.REF_ESTADO as desEstado4 FROM SVT_PLAN_SFPA SPSFPA INNER JOIN SVC_CONTRATANTE SC on SC.ID_CONTRATANTE = SPSFPA.ID_TITULAR INNER JOIN SVC_PERSONA SP on SP.ID_PERSONA = SC.ID_PERSONA INNER JOIN SVT_DOMICILIO SD on SD.ID_DOMICILIO = SC.ID_DOMICILIO LEFT JOIN SVT_TITULAR_BENEFICIARIOS STB2 "
			+ "on STB2.ID_TITULAR_BENEFICIARIOS = SPSFPA.ID_TITULAR_SUBSTITUTO LEFT JOIN SVC_PERSONA SP2 on SP2.ID_PERSONA = STB2.ID_PERSONA LEFT JOIN SVT_DOMICILIO SD2 on SD2.ID_DOMICILIO = STB2.ID_DOMICILIO LEFT JOIN SVT_TITULAR_BENEFICIARIOS STB3 on STB3.ID_TITULAR_BENEFICIARIOS = SPSFPA.ID_BENEFICIARIO_1 "
			+ "LEFT JOIN SVC_PERSONA SP3 on SP3.ID_PERSONA = STB3.ID_PERSONA LEFT JOIN SVT_DOMICILIO SD3 on SD3.ID_DOMICILIO = STB3.ID_DOMICILIO LEFT JOIN SVT_TITULAR_BENEFICIARIOS STB4 on STB4.ID_TITULAR_BENEFICIARIOS = SPSFPA.ID_BENEFICIARIO_2 LEFT JOIN SVC_PERSONA SP4 on SP4.ID_PERSONA = STB4.ID_PERSONA "
			+ "LEFT JOIN SVT_DOMICILIO SD4 on SD4.ID_DOMICILIO = STB4.ID_DOMICILIO INNER JOIN SVC_VELATORIO SV on SV.ID_VELATORIO = SPSFPA.ID_VELATORIO INNER JOIN SVC_TIPO_PAGO_MENSUAL STPM ON STPM.ID_TIPO_PAGO_MENSUAL  = SPSFPA.ID_TIPO_PAGO_MENSUAL INNER JOIN SVC_ESTATUS_PLAN_SFPA SEPS ON "
			+ "SEPS.ID_ESTATUS_PLAN_SFPA  = SPSFPA.ID_ESTATUS_PLAN_SFPA WHERE SPSFPA.ID_PLAN_SFPA = #{idPlanSfpa} AND SPSFPA.IND_ACTIVO = 1")
	public DetallePlanSFPAResponse selectVerdetallePlanSFPA(Integer idPlanSfpa);
	
	@Select("select pg.idPagoSFPA, pg.idEstatus, CONCAT(cast((@ROW := @ROW + 1) as VARCHAR(255)), '/', ( select COUNT(pf.ID_PAGO_SFPA) from SVT_PAGO_SFPA pf where pf.IND_ACTIVO = 1 and pf.ID_PLAN_SFPA = pg.idPlanSFPA)) as noPagos, "
			+ "pg.idPlanSFPA, pg.velatorio, DATE_FORMAT(pg.fechaParcialidad, '%d/%m/%Y') as fechaParcialidad, pg.importeMensual, pg.estatusPago, pg.importePagado, case when pg.importePagado < pg.importeMensual && pg.fechaParcialidad = CURDATE() "
			+ "then true when pg.importePagado = pg.importeMensual then false else false end as validaPago, pg.importePagado , pg.importeMensual , pg.fechaParcialidad , case when pg.idEstatus = 2 then 0 when month(pg.fechaParcialidad) = month(CURDATE()) "
			+ "&& ((pg.importeFaltante + pg.importeMensual) - pg.importePagadoBitacora) > 0 then (pg.importeFaltante + pg.importeMensual) - pg.importePagadoBitacora when pg.importeMensual - pg.importePagado > 0 then pg.importeMensual - pg.importePagado "
			+ "else pg.importeMensual end as importeAcumulado, pg.folioRecibo, (select GROUP_CONCAT(sm.DES_METODO_PAGO) from SVC_BITACORA_PAGO_ANTICIPADO bp inner join SVC_METODO_PAGO sm on bp.ID_METODO_PAGO = sm.ID_METODO_PAGO "
			+ "where bp.IND_ACTIVO = 1 and bp.ID_PAGO_SFPA = pg.idPagoSFPA) as metodoPago from ( select ps.ID_PAGO_SFPA as idPagoSFPA, ps.ID_PLAN_SFPA as idPlanSFPA, ps.ID_ESTATUS_PAGO as idEstatus, v.DES_VELATORIO as velatorio,"
			+ " ps.FEC_PARCIALIDAD as fechaParcialidad, ps.IMP_MONTO_MENSUAL as importeMensual, ep.DES_ESTATUS_PAGO_ANTICIPADO as estatusPago, ( select (IFNULL(SUM(bpa.IMP_PAGO), 0) + IFNULL(SUM(bpa.IMP_AUTORIZADO_VALE_PARITARIO), 0)) "
			+ "from SVC_BITACORA_PAGO_ANTICIPADO bpa where bpa.IND_ACTIVO = 1 and bpa.ID_PAGO_SFPA = ps.ID_PAGO_SFPA) as importePagado, ps.IND_ACTIVO, ( select IFNULL(SUM(sps.IMP_MONTO_MENSUAL), 0) from SVT_PAGO_SFPA sps where "
			+ "sps.ID_ESTATUS_PAGO = 2 and sps.IND_ACTIVO = 1 and sps.FEC_PARCIALIDAD <= CURDATE() and sps.ID_PLAN_SFPA = ps.ID_PLAN_SFPA) as importeFaltante, ( select IFNULL(SUM(bpaa.IMP_PAGO), 0) + IFNULL(SUM(bpaa.IMP_AUTORIZADO_VALE_PARITARIO), 0) "
			+ "from SVT_PAGO_SFPA sps join SVC_BITACORA_PAGO_ANTICIPADO bpaa on bpaa.ID_PAGO_SFPA = sps.ID_PAGO_SFPA and bpaa.IND_ACTIVO = 1 where sps.IND_ACTIVO = 1 and sps.ID_PLAN_SFPA = ps.ID_PLAN_SFPA) as importePagadoBitacora, ps.IMP_MONTO_MENSUAL, "
			+ "IFNULL(ps.REF_FOLIO_RECIBO, '') as folioRecibo from SVT_PAGO_SFPA ps join SVT_PLAN_SFPA pls on pls.ID_PLAN_SFPA = ps.ID_PLAN_SFPA join SVC_VELATORIO v on v.ID_VELATORIO = pls.ID_VELATORIO join SVC_ESTATUS_PAGO_ANTICIPADO ep on "
			+ "ep.ID_ESTATUS_PAGO_ANTICIPADO = ps.ID_ESTATUS_PAGO) as pg, ( select @ROW := 0) r where pg.idPlanSFPA = #{idPlanSfpa} and pg.IND_ACTIVO = 1")
	public List<PagoSFPAResponse> selectVerdetallePagoSFPA(Integer idPlanSfpa);
	
	@Select("SELECT TIP_PARAMETRO AS imgCheck FROM SVC_PARAMETRO_SISTEMA sps WHERE sps.ID_FUNCIONALIDAD = 25 AND sps.DES_PARAMETRO = 'IMAGEN_CHECK'")
	public String getImagenCheck();
	
	@Select("SELECT TIP_PARAMETRO AS firmDir FROM SVC_PARAMETRO_SISTEMA sps WHERE sps.ID_FUNCIONALIDAD = 25 AND sps.DES_PARAMETRO = 'FIRMA_DIRECTORA'")
	public String getImagenFirma();
	
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
