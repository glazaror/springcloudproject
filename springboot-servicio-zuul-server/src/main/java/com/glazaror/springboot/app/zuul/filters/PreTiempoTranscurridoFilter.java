package com.glazaror.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

// Zuul esta compuesto principalmente por filtros. Los tipos de filtro son 3:
// 1. Pre: Se ejecuta antes de que el request sea enrutado (se utiliza para pasar datos/atributos al request para que sean utilizados en los otros filters)
// 2. Post: Se ejecuta despues de que el request ha sido enrutado (se usa para manipular la respuesta - tipicamente las cabeceras)
// 3. Route: Se ejecuta durante el enrutado del request, aqui se resuelve la ruta (se usa para la comunicacion con el microservicio)
// 4. Filtro para errores
// Por debajo se tienen estos filtros ya implementados (RibbonRoutingFilter) el cual utiliza ribbon, hystrix y un cliente http para la comunicacion y para asi enrutar los request. 

// Tenemos que registrar el filter como un bean de spring con la anotacion @Component
@Component
public class PreTiempoTranscurridoFilter extends ZuulFilter {
	
	// simple logger 4j - sl4j
	private static Logger log = LoggerFactory.getLogger(PreTiempoTranscurridoFilter.class);

	@Override
	public boolean shouldFilter() {
		// para validar si es que este filtro se debe ejecutar o no
		// podemos verificar si es que existe un parametro en la ruta (requestParam), si es que el usuario esta autenticado, etc
		// para este ejemplo retornamos true... que se ejecute siempre
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		// Se ejecuta la logica del filtro
		// obtenemos el HttpRequest
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		log.info(String.format("%s request enrutado a %s", request.getMethod(), request.getRequestURL().toString()));
		
		Long tiempoInicio = System.currentTimeMillis();
		request.setAttribute("tiempoInicio", tiempoInicio);
		return null;
	}

	@Override
	public String filterType() {
		// Tipo de filtro "pre"
		return "pre";
	}

	@Override
	public int filterOrder() {
		// el orden 1
		return 1;
	}

}
