package com.imss.sivimss.cpsf.service.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.imss.sivimss.cpsf.model.request.Contratante;
import com.imss.sivimss.cpsf.model.request.PlanSFPA;
import com.imss.sivimss.cpsf.model.request.UsuarioDto;
import com.imss.sivimss.cpsf.utils.ConsultaConstantes;
import com.imss.sivimss.cpsf.utils.SelectQueryUtil;

@Service
public class BeanQuerys {

	private static final Logger log = LoggerFactory.getLogger(BeanQuerys.class);

	public String queryGetArticulos() {
		return "SELECT DISTINCT "
				+ "    SA.ID_ARTICULO AS idArticulo, "
				+ "    SA.REF_ARTICULO AS nombreArticulo "
				+ "FROM "
				+ "    SVT_ARTICULO SA "
				+ "INNER JOIN SVT_INVENTARIO_ARTICULO STI ON "
				+ "    SA.ID_ARTICULO = STI.ID_ARTICULO AND STI.IND_ESTATUS NOT IN(2, 3) AND STI.ID_INVE_ARTICULO NOT IN( "
				+ "    SELECT "
				+ "        IFNULL(STP.ID_INVE_ARTICULO, 0) "
				+ "    FROM "
				+ "        SVC_DETALLE_CARAC_PRESUP_TEMP STP "
				+ "    WHERE "
				+ "        STP.IND_ACTIVO = 1 AND DATE_FORMAT(STP.TIM_ALTA, 'YY-MM-DD') = DATE_FORMAT(CURRENT_TIMESTAMP(), 'YY-MM-DD') AND TIMESTAMPDIFF( "
				+ "            MINUTE, "
				+ "            DATE_ADD(STP.TIM_ALTA, INTERVAL 4 HOUR), "
				+ "            CURRENT_TIMESTAMP()) <= 0 "
				+ "        ) AND STI.ID_INVE_ARTICULO NOT IN( "
				+ "        SELECT "
				+ "            IFNULL(SDCP.ID_INVE_ARTICULO, 0) "
				+ "        FROM "
				+ "            SVC_DETALLE_CARAC_PRESUP SDCP "
				+ "        WHERE "
				+ "            SDCP.IND_ACTIVO = 1 "
				+ "    ) "
				+ "INNER JOIN SVT_ORDEN_ENTRADA SOE2 ON "
				+ "    SOE2.ID_ODE = STI.ID_ODE "
				+ "INNER JOIN SVT_CONTRATO SC ON "
				+ "    SC.ID_CONTRATO = SOE2.ID_CONTRATO "
				+ "INNER JOIN SVT_CONTRATO_ARTICULOS SCA ON "
				+ "    SCA.ID_CONTRATO = SC.ID_CONTRATO AND STI.ID_ARTICULO = SCA.ID_ARTICULO "
				+ "WHERE "
				+ "    STI.ID_TIPO_ASIGNACION_ART = 1 AND SA.ID_CATEGORIA_ARTICULO = 1 AND STI.ID_VELATORIO = 1 "
				+ "UNION "
				+ "SELECT DISTINCT "
				+ "    SA.ID_ARTICULO AS idArticulo, "
				+ "    SA.REF_ARTICULO AS nombreArticulo "
				+ "FROM "
				+ "    SVT_ARTICULO SA "
				+ "INNER JOIN SVT_INVENTARIO_ARTICULO STI ON "
				+ "    SA.ID_ARTICULO = STI.ID_ARTICULO AND STI.IND_ESTATUS NOT IN(2, 3) AND STI.ID_INVE_ARTICULO NOT IN( "
				+ "    SELECT "
				+ "        IFNULL(STP.ID_INVE_ARTICULO, 0) "
				+ "    FROM "
				+ "        SVC_DETALLE_CARAC_PRESUP_TEMP STP "
				+ "    WHERE "
				+ "        STP.IND_ACTIVO = 1 AND DATE_FORMAT(STP.TIM_ALTA, 'YY-MM-DD') = DATE_FORMAT(CURRENT_TIMESTAMP(), 'YY-MM-DD') AND TIMESTAMPDIFF( "
				+ "            MINUTE, "
				+ "            DATE_ADD(STP.TIM_ALTA, INTERVAL 4 HOUR), "
				+ "            CURRENT_TIMESTAMP()) <= 0 "
				+ "        ) AND STI.ID_INVE_ARTICULO IN( "
				+ "        SELECT "
				+ "            IFNULL(SDCP.ID_INVE_ARTICULO, 0) "
				+ "        FROM "
				+ "            SVC_DETALLE_CARAC_PRESUP SDCP "
				+ "        INNER JOIN SVC_ATAUDES_DONADOS STA ON "
				+ "            STA.ID_INVE_ARTICULO = SDCP.ID_INVE_ARTICULO "
				+ "        INNER JOIN SVT_INVENTARIO_ARTICULO STAI ON "
				+ "            STAI.ID_INVE_ARTICULO = SDCP.ID_INVE_ARTICULO "
				+ "        WHERE "
				+ "            SDCP.IND_ACTIVO = 1 AND STAI.ID_TIPO_ASIGNACION_ART = 1 "
				+ "    ) "
				+ "INNER JOIN SVT_ORDEN_ENTRADA SOE2 ON "
				+ "    SOE2.ID_ODE = STI.ID_ODE "
				+ "INNER JOIN SVT_CONTRATO SC ON "
				+ "    SC.ID_CONTRATO = SOE2.ID_CONTRATO "
				+ "INNER JOIN SVT_CONTRATO_ARTICULOS SCA ON "
				+ "    SCA.ID_CONTRATO = SC.ID_CONTRATO AND STI.ID_ARTICULO = SCA.ID_ARTICULO "
				+ "WHERE "
				+ "    SA.ID_CATEGORIA_ARTICULO = 1 AND STI.ID_VELATORIO = 1";

	}

	public String queryTodosArticulos() {
		return "SELECT "
				+ "    SA.ID_ARTICULO AS idArticulo, "
				+ "    SA.REF_ARTICULO AS nombreArticulo "
				+ "FROM "
				+ "    SVT_ARTICULO SA ";
	}

	public String detallePlanSFPA(String cveUsuario) {
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("SPSFPA.ID_PLAN_SFPA as idPlanSfpa",
				"IFNULL(SPSFPA.NUM_FOLIO_PLAN_SFPA, '') as numFolioPlanSfpa", "IFNULL(SP.CVE_CURP, '') as curpTitular",
				"IFNULL(CONCAT_WS(' ',SP.NOM_PERSONA,SP.NOM_PRIMER_APELLIDO,SP.NOM_SEGUNDO_APELLIDO), '') AS nombreTitular",
				"IFNULL(SP2.CVE_CURP, '') as curpSubtitular",
				"IFNULL(CONCAT_WS(' ',SP2.NOM_PERSONA,SP2.NOM_PRIMER_APELLIDO,SP2.NOM_SEGUNDO_APELLIDO), '') AS nombreSubtitular")
				.from(ConsultaConstantes.SVT_PLAN_SFPA_SPSFPA)
				.innerJoin(ConsultaConstantes.SVC_CONTRATANTE_SC, "SC.ID_CONTRATANTE = SPSFPA.ID_TITULAR")
				.innerJoin(ConsultaConstantes.SVC_PERSONA_SP, ConsultaConstantes.SP_ID_PERSONA_SC_ID_PERSONA)
				.innerJoin("SVT_USUARIOS SU", "SU.ID_PERSONA = SP.ID_PERSONA")
				.innerJoin(ConsultaConstantes.SVT_DOMICILIO_SD, "SD.ID_DOMICILIO = SC.ID_DOMICILIO")
				.leftJoin("SVT_TITULAR_BENEFICIARIOS STB2",
						"STB2.ID_TITULAR_BENEFICIARIOS = SPSFPA.ID_TITULAR_SUBSTITUTO")
				.leftJoin("SVC_PERSONA SP2", "SP2.ID_PERSONA = STB2.ID_PERSONA")
				.leftJoin("SVT_DOMICILIO SD2", "SD2.ID_DOMICILIO = STB2.ID_DOMICILIO")
				.innerJoin(ConsultaConstantes.SVC_VELATORIO_SV, "SV.ID_VELATORIO = SPSFPA.ID_VELATORIO")
				.where("SU.CVE_USUARIO = :cveUsuario").setParameter("cveUsuario", cveUsuario)
				.groupBy("SPSFPA.ID_PLAN_SFPA");
		String consulta = queryUtil.build();
		log.info(consulta);
		return consulta;
	}

	public String consultaTipoContratacion() {
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("STC.ID_TIPO_CONTRATACION AS ID_TIPO_CONTRATACION",
				"STC.DES_TIPO_CONTRATACION AS DES_TIPO_CONTRATACION").from("SVC_TIPO_CONTRATACION STC");
		return queryUtil.build();
	}

	public String consultaTipoPagoMensual() {
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil
				.select("STPM.ID_TIPO_PAGO_MENSUAL AS ID_TIPO_PAGO_MENSUAL",
						"STPM.DES_TIPO_PAGO_MENSUAL AS DES_TIPO_PAGO_MENSUAL")
				.from(ConsultaConstantes.SVC_TIPO_PAGO_MENSUAL_STPM);
		return queryUtil.build();
	}

	public String consultaPromotores() {
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("SP.ID_PROMOTOR AS ID_PROMOTOR",
				"CONCAT(IFNULL(SP.NOM_PROMOTOR,''),' ',IFNULL(SP.NOM_PAPELLIDO,''),' ',IFNULL(SP.NOM_SAPELLIDO,'')) AS NOMBRE")
				.from("SVT_PROMOTOR SP").where(ConsultaConstantes.SP_IND_ACTIVO_1);
		return queryUtil.build();
	}

	public String consultaPaquetes() {
		SelectQueryUtil selectQueryVelatorio = new SelectQueryUtil();
		selectQueryVelatorio
				.select("SP.ID_PAQUETE AS idPaquete", "SP.REF_PAQUETE_NOMBRE AS nomPaquete",
						"SP.REF_PAQUETE_DESCRIPCION AS descPaquete", "IFNULL(SP.MON_PRECIO,0) as monPrecio")
				.from(ConsultaConstantes.SVT_PAQUETE_SP).where("IFNULL(SP.ID_PAQUETE ,0) > 0")
				.and(ConsultaConstantes.SP_IND_ACTIVO_1).and("SP.IND_ONLINE=1").and("SP.IND_PA=1");
		return selectQueryVelatorio.build();
	}

	public String obtenerFolioPlanSFPA(PlanSFPA planSFPARequest, UsuarioDto usuarioDto) {
		SelectQueryUtil velatorio = new SelectQueryUtil();
		velatorio.select("SUBSTRING(UPPER(SV.DES_VELATORIO),1,3)").from(ConsultaConstantes.SVC_VELATORIO_SV)
				.where("SV.ID_VELATORIO = :idVelatorio")
				.setParameter(ConsultaConstantes.ID_VELATORIO, usuarioDto.getIdVelatorio());

		SelectQueryUtil paquete = new SelectQueryUtil();
		paquete.select("SUBSTRING(UPPER(SP.REF_PAQUETE_NOMBRE),1,3)").from(ConsultaConstantes.SVT_PAQUETE_SP)
				.where("SP.ID_PAQUETE = :idPaquete").setParameter("idPaquete", planSFPARequest.getIdPaquete());

		SelectQueryUtil numeroPagoMensual = new SelectQueryUtil();
		numeroPagoMensual.select("STPM.DES_TIPO_PAGO_MENSUAL").from(ConsultaConstantes.SVC_TIPO_PAGO_MENSUAL_STPM)
				.where("STPM.ID_TIPO_PAGO_MENSUAL = :idTipoPagoMensual")
				.setParameter("idTipoPagoMensual", planSFPARequest.getIdTipoPagoMensual());

		SelectQueryUtil numeroConsecutivo = new SelectQueryUtil();
		numeroConsecutivo.select("IFNULL(MAX(SPSFPA.ID_PLAN_SFPA),0) + 1")
				.from(ConsultaConstantes.SVT_PLAN_SFPA_SPSFPA);

		SelectQueryUtil selectQueryUtil = new SelectQueryUtil();
		selectQueryUtil
				.select("CONCAT_WS('-',(" + velatorio.build() + "),(" + paquete.build() + "),("
						+ numeroPagoMensual.build() + "),(" + numeroConsecutivo.build() + "))")
				.from("DUAL )");
		return selectQueryUtil.build();
	}

	public String consultaExisteTitularBeneficiarios(Contratante contratanteRequest) {
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil
				.select("STB.ID_TITULAR_BENEFICIARIOS AS idTitularBeneficiarios", "SD.ID_DOMICILIO AS idDomicilio",
						ConsultaConstantes.SP_ID_PERSONA_AS_ID_PERSONA)
				.from(ConsultaConstantes.SVC_PERSONA_SP)
				.leftJoin("SVT_TITULAR_BENEFICIARIOS STB", "SP.ID_PERSONA = STB.ID_PERSONA")
				.leftJoin(ConsultaConstantes.SVT_DOMICILIO_SD, "SD.ID_DOMICILIO = STB.ID_DOMICILIO")
				.where("IFNULL(SP.ID_PERSONA ,0) > 0");
		if (contratanteRequest.getCurp() != null && !contratanteRequest.getCurp().isEmpty()) {
			queryUtil.and("SP.CVE_CURP = :curp").setParameter("curp", contratanteRequest.getCurp());
		}
		if (contratanteRequest.getRfc() != null && !contratanteRequest.getRfc().isEmpty()) {
			queryUtil.and("SP.CVE_RFC = :rfc").setParameter("rfc", contratanteRequest.getRfc());
		}
		if (contratanteRequest.getIne() != null) {
			queryUtil.and("SP.NUM_INE = :ine").setParameter("ine", contratanteRequest.getIne());
		}
		queryUtil.orderBy("SP.ID_PERSONA DESC LIMIT 1");
		return queryUtil.build();
	}

	public String consultaExisteContratante(Contratante contratanteRequest) {
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil
				.select("SC.ID_CONTRATANTE AS idContratante", "SD.ID_DOMICILIO AS idDomicilio",
						ConsultaConstantes.SP_ID_PERSONA_AS_ID_PERSONA)
				.from(ConsultaConstantes.SVC_PERSONA_SP)
				.leftJoin(ConsultaConstantes.SVC_CONTRATANTE_SC, ConsultaConstantes.SP_ID_PERSONA_SC_ID_PERSONA)
				.leftJoin(ConsultaConstantes.SVT_DOMICILIO_SD, "SD.ID_DOMICILIO = SC.ID_DOMICILIO")
				.where("IFNULL(SP.ID_PERSONA , 0) > 0");
		if (contratanteRequest.getCurp() != null && !contratanteRequest.getCurp().isEmpty()) {
			queryUtil.and("SP.CVE_CURP = :curp").setParameter("curp", contratanteRequest.getCurp());
		}
		if (contratanteRequest.getRfc() != null && !contratanteRequest.getRfc().isEmpty()) {
			queryUtil.and("SP.CVE_RFC = :rfc").setParameter("rfc", contratanteRequest.getRfc());
		}
		if (contratanteRequest.getIne() != null) {
			queryUtil.and("SP.NUM_INE = :ine").setParameter("ine", contratanteRequest.getIne());
		}
		queryUtil.orderBy("SP.ID_PERSONA DESC LIMIT 1");
		return queryUtil.build();
	}

	public String consultaValidaAfiliado(Contratante contratanteRequest) {
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		StringBuilder subQuery = new StringBuilder();
		if (contratanteRequest.getCurp() != null) {
			subQuery.append(" AND SP.CVE_CURP = '").append(contratanteRequest.getCurp())
					.append(ConsultaConstantes.COMILLA_SIMPLE);
		}
		if (contratanteRequest.getRfc() != null) {
			subQuery.append(" AND SP.CVE_RFC = '").append(contratanteRequest.getRfc())
					.append(ConsultaConstantes.COMILLA_SIMPLE);
		}
		if (contratanteRequest.getIne() != null) {
			subQuery.append(" AND SP.NUM_INE = '").append(contratanteRequest.getIne())
					.append(ConsultaConstantes.COMILLA_SIMPLE);
		}
		queryUtil.select("SPSFPA.ID_PLAN_SFPA AS ID_PLAN_SFPA", "SPSFPA.NUM_FOLIO_PLAN_SFPA AS NUM_FOLIO_PLAN_SFPA")
				.from(ConsultaConstantes.SVT_PLAN_SFPA_SPSFPA)
				.where("SPSFPA.ID_TITULAR = (SELECT SC.ID_CONTRATANTE AS IDCONTRATANTE"
						.concat(" FROM SVC_CONTRATANTE SC ")
						.concat(" INNER JOIN SVC_PERSONA SP ON SP.ID_PERSONA = SC.ID_PERSONA")
						.concat(" INNER JOIN SVT_DOMICILIO SD ON SD.ID_DOMICILIO = SC.ID_DOMICILIO ")
						.concat(" WHERE IFNULL(SC.ID_CONTRATANTE ,0) > 0 ").concat(subQuery.toString())
						.concat(" ORDER BY SC.ID_CONTRATANTE DESC LIMIT 1").concat(")"))
				.and("SPSFPA.ID_ESTATUS_PLAN_SFPA NOT IN (6)");
		return queryUtil.build();
	}

	public String folioPlanSfpa(Integer idPlanSfpa) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT NUM_FOLIO_PLAN_SFPA AS folioPlanSFPA FROM ")
				.append(ConsultaConstantes.SVT_PLAN_SFPA_SPSFPA).append(" WHERE SPSFPA.ID_PLAN_SFPA = ")
				.append(idPlanSfpa);
		return query.toString();
	}

	public String consultarPagoSfpa(Integer idPlanSfpa) {
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil
				.select("DATE_FORMAT(PSFPA.FEC_PARCIALIDAD,'%d/%m/%Y') AS FEC_PARCIALIDAD",
						"DATE_FORMAT(PSFPA.FEC_ALTA, '%d/%m/%Y') as FEC_ALTA")
				.from("SVT_PAGO_SFPA PSFPA").where("IFNULL(PSFPA.ID_PAGO_SFPA ,0) > 0").and("PSFPA.ID_ESTATUS_PAGO = 8")
				.and("PSFPA.ID_PLAN_SFPA = :idPlanSfpa").setParameter(ConsultaConstantes.ID_PLAN_SFPA, idPlanSfpa)
				.orderBy("PSFPA.ID_PAGO_SFPA  ASC LIMIT 1");
		return queryUtil.build();
	}

	public String consultaPlanSFPA(Integer idPlanSfpa) {
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select("SPSFPA.ID_TITULAR as idTitular", ConsultaConstantes.SP_ID_PERSONA_AS_ID_PERSONA,
				"IFNULL(SP.NOM_PERSONA, '') AS nomPersona", "IFNULL(SP.NOM_PRIMER_APELLIDO, '') AS nomApellidoPaterno",
				"IFNULL(SP.NOM_SEGUNDO_APELLIDO, '') AS nomApellidoMaterno", "IFNULL(SP.REF_CORREO , '') AS correo")
				.from(ConsultaConstantes.SVT_PLAN_SFPA_SPSFPA)
				.innerJoin(ConsultaConstantes.SVC_CONTRATANTE_SC, "SC.ID_CONTRATANTE = SPSFPA.ID_TITULAR")
				.innerJoin(ConsultaConstantes.SVC_PERSONA_SP, ConsultaConstantes.SP_ID_PERSONA_SC_ID_PERSONA)
				.where("IFNULL(SPSFPA.ID_PLAN_SFPA ,0) > 0").and("SPSFPA.ID_PLAN_SFPA =:idPlanSfpa")
				.setParameter(ConsultaConstantes.ID_PLAN_SFPA, idPlanSfpa);
		return queryUtil.build();
	}

	public String consultaServiciosPaquete(Integer idPaquete) {
		SelectQueryUtil queryUtil = new SelectQueryUtil();
		queryUtil.select(
				"ss.DES_SERVICIO AS servicio")
				.from("SVT_PAQUETE sp")
				.innerJoin("SVT_PAQUETE_SERVICIO ser", "sp.ID_PAQUETE = ser.ID_PAQUETE")
				.innerJoin("SVT_SERVICIO ss", "ser.ID_SERVICIO = ss.ID_SERVICIO")
				.where("sp.IND_ACTIVO = 1\r\n"
						+ " AND sp.IND_ONLINE = 1 "
						+ " AND ser.IND_ACTIVO = 1")
				.and("sp.ID_PAQUETE=" + idPaquete);
		return queryUtil.build();
	}

	public String validarCurpRfc(String rfc, String curp) {
		StringBuilder query = new StringBuilder("SELECT SP.ID_PERSONA idPersona, "
				+ "SP.NOM_PERSONA nomPersona,"
				+ " SP.NOM_PRIMER_APELLIDO materno,"
				+ " SP.NOM_SEGUNDO_APELLIDO paterno,"
				+ " SP.CVE_RFC rfc,"
				+ " SV.ID_CONTRATANTE idContratante,"
				+ " SP.CVE_CURP curp,"
				+ " SV.IND_ACTIVO estatus,"
				+ "SP.CVE_NSS nss,"
				+ "DATE_FORMAT(SP.FEC_NAC, '%d/%m/%Y') fecNacimiento, "
				+ "SP.NUM_SEXO idSexo, "
				+ "CASE "
				+ "WHEN SP.NUM_SEXO=1 THEN 'MUJER' "
				+ "WHEN SP.NUM_SEXO=2 THEN 'HOMBRE' "
				+ "ELSE 'OTRO' "
				+ "END sexo, "
				+ "SP.REF_OTRO_SEXO otroSexo, "
				+ "IF(SP.ID_PAIS=119, 'MEXICANA', 'EXTRANJERA') nacionalidad, "
				+ "PA.DES_PAIS pais, "
				+ "SP.ID_PAIS idPais, "
				+ "EDO.DES_ESTADO lugarNac, "
				+ "SP.ID_ESTADO idLugarNac, "
				+ "SP.REF_TELEFONO tel, "
				+ "SP.REF_CORREO correo, "
				+ "DOM.REF_CALLE calle, "
				+ "DOM.NUM_EXTERIOR numExt, "
				+ "DOM.NUM_INTERIOR numInt, "
				+ "DOM.REF_CP cp, "
				+ "DOM.REF_COLONIA colonia, "
				+ "DOM.REF_MUNICIPIO municipio, "
				+ "DOM.REF_ESTADO estado, "
				+ "USR.ID_USUARIO idUsuario, "
				+ "USR.CVE_USUARIO usr"
				+ " FROM ");
		query.append("SVC_CONTRATANTE SV ");
		query.append("INNER JOIN SVC_PERSONA SP ON SV.ID_PERSONA = SP.ID_PERSONA ");
		query.append("INNER JOIN SVC_PAIS PA ON SP.ID_PAIS = PA.ID_PAIS ");
		query.append("LEFT JOIN SVC_ESTADO EDO ON SP.ID_ESTADO = EDO.ID_ESTADO ");
		query.append("INNER JOIN SVT_DOMICILIO DOM ON SV.ID_DOMICILIO = DOM.ID_DOMICILIO ");
		query.append("LEFT JOIN SVT_USUARIOS USR ON USR.ID_PERSONA = SP.ID_PERSONA ");
		if (curp != null) {
			query.append(" WHERE SP.CVE_CURP = '" + curp + "'");
		} else {
			query.append(" WHERE SP.CVE_RFC =  '" + rfc + "'");
		}
		query.append(" LIMIT 1");
		return query.toString();
	}

	public String detallePlanSFPAContratante(Integer idVelatorio, Integer idContratante) {
		String consulta = "SELECT\r\n"
				+ "	c.ID_CONTRATANTE AS idContratante,   \r\n"
				+ "			 c.ID_PERSONA AS idPersona,\r\n"
				+ "	c.ID_DOMICILIO AS idDomicilio,  \r\n"
				+ "			 ifnull(p.CVE_RFC, '') AS rfc,\r\n"
				+ "	IFNULL(p.CVE_CURP,'') AS curp,\r\n"
				+ "	IFNULL(p.CVE_NSS,'') AS nss,   \r\n"
				+ "			 p.NOM_PERSONA AS nombre,\r\n"
				+ "	p.NOM_PRIMER_APELLIDO AS primerApellido,  \r\n"
				+ "			 p.NOM_SEGUNDO_APELLIDO AS segundoApellido,  \r\n"
				+ "			 ifnull(c.CVE_MATRICULA, '') as matricula, \r\n"
				+ "			 p.NUM_SEXO AS idSexo,   \r\n"
				+ "			 case\r\n"
				+ "		when p.NUM_SEXO = 1 then 'Mujer'\r\n"
				+ "		when p.NUM_SEXO = 2 then 'Hombre'\r\n"
				+ "		ELSE 'Otro'\r\n"
				+ "	END sexo,  \r\n"
				+ "			 ifnull(p.REF_OTRO_SEXO, '') AS otroSexo,   \r\n"
				+ "			 DATE_FORMAT(p.FEC_NAC, '%d-%m-%Y') AS fechaNacimiento,  \r\n"
				+ "			 IFNULL(p.ID_ESTADO,0) AS idEstadoNacimiento,  \r\n"
				+ "			 IFNULL(p.ID_PAIS,0) AS idPaisNacimiento,  \r\n"
				+ "			 p.REF_TELEFONO AS telefono,  \r\n"
				+ "			 p.REF_CORREO AS correo,  \r\n"
				+ "			 p.TIP_PERSONA AS tipoPersona,  \r\n"
				+ "			 e.DES_ESTADO AS estado , \r\n"
				+ "			 c.ID_DOMICILIO AS idDomicilio, \r\n"
				+ "			(\r\n"
				+ "	SELECT\r\n"
				+ "		d.DES_DELEGACION\r\n"
				+ "	FROM\r\n"
				+ "		SVC_VELATORIO v\r\n"
				+ "	JOIN SVC_DELEGACION d ON\r\n"
				+ "		d.ID_DELEGACION = v.ID_DELEGACION\r\n"
				+ "	WHERE\r\n"
				+ "		v.ID_VELATORIO = " + idVelatorio + ") AS delegacion , \r\n"
				+ "			 DATE_FORMAT(DATE_ADD( CURDATE(), INTERVAL 1 DAY), '%d-%m-%Y') AS fecha\r\n"
				+ "FROM\r\n"
				+ "	SVC_CONTRATANTE c\r\n"
				+ "JOIN SVC_PERSONA p ON\r\n"
				+ "	p.ID_PERSONA = c.ID_PERSONA\r\n"
				+ "LEFT JOIN SVC_ESTADO e ON\r\n"
				+ "	e.ID_ESTADO = p.ID_ESTADO\r\n"
				+ "WHERE\r\n"
				+ "	c.ID_CONTRATANTE = " + idContratante;
		log.info(consulta);
		return consulta;
	}

	public String consultaPagoMensual() {
		String consulta = "SELECT ID_TIPO_PAGO_MENSUAL AS value, DES_TIPO_PAGO_MENSUAL AS label\r\n"
				+ "FROM SVC_TIPO_PAGO_MENSUAL\r\n"
				+ "WHERE IND_ACTIVO =1";
		log.info(consulta);
		return consulta;
	}
}
