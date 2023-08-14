package com.utec.pft.api;

import com.utec.pft.entites.Estacion;
import com.utec.pft.entites.Formulario;
import com.utec.pft.entites.Registro;
import com.utec.pft.entites.Usuario;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceApi {
//    Rest Usuarios
    @GET("usuarios/listarUsuarios")
    public abstract Call<List<Usuario>> listarUsuarios();

    @GET("usuarios/login{usuario}-{password}")
    public abstract Call<Usuario> login(@Path("usuario") String usuario,@Path("password") String password);

    @GET("usuarios/ldap{usuario}-{password}")
    public abstract Call<Usuario> ldap(@Path("usuario") String usuario,@Path("password") String password);

//    Rest Formularios
    @GET("formularios/listarFormularios")
    public abstract Call<List<Formulario>> listarFormularios();

//    Rest Estaciones
    @GET("estaciones/listarEstaciones")
    public abstract Call<List<Estacion>> listarEstaciones();

//    Rest RegistrosCA
    @POST("registros/altaRegistro{fecha}")
    public abstract  Call<Boolean> altaRegistro(@Body Registro registro, @Path("fecha") String fecha);

    @GET("registros/listarRegistros")
    public abstract Call<List<Registro>> listarRegistros();

    @POST("registros/bajaRegistro{id}")
    public abstract Call<Boolean> bajaRegistro(@Path("id") Long id);

    @POST("registros/modificarRegistro{fecha}")
    public abstract Call<Boolean> modificarRegistro(@Body Registro registro, @Path("fecha") String fecha);






}
