package com.co.meli.coupon.exception;

import lombok.Getter;

@Getter
public enum ErrorMeli {
	
	LISTA_ERRONEA(404,"Lista vacia a consultar precio"),
	NO_RESPUESTA(404,"No hay posibilidades."),
	ERROR_APLICACION(500,"Error de aplicacion");
	
	private Integer codigo;
	private String mensaje;
	
	ErrorMeli(Integer codigo,String mensaje){
		this.codigo = codigo;
		this.mensaje = mensaje;
	}
	
	public MeliException throwError() {
		return new MeliException(this.mensaje);
	}

}
