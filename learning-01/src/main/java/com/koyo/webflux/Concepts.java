package com.koyo.webflux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Concepts {

    private static final Logger log = LoggerFactory.getLogger(Concepts.class);

    // El patron de diseño Observable
    /*
        Un componente de software que está sujeto a cambios y debe notificar
        los cambios hacia multiples componentes a los cuales los llamamos observers
    */


    public static void main(String[] args) {
        //introduccion();

    }

    public static void introduccion() {
        List<Integer> elementFromMono = new ArrayList<>();
        List<Integer> elementFromFlux = new ArrayList<>();

        /// Introducción Project Reactor
        // Creamos un Mono
        // Inicia ejecutado el valor enviado, en este caso una constante
        Mono<Integer> mono = Mono.just(727);

        // Se ejecutará cuando alguien se subscribe al valor
        // osea que lo que esta dentro de la funcion será llamado cuando alguien se subscribe
        Mono<String> mono2 = Mono.fromSupplier(() -> {
            throw new RuntimeException("Excepción ocurrida");
        });

        Flux<Integer> flux = Flux.just(1, 2, 3, 4);

        Flux<String> flux2 = Flux.fromArray(new String[]{"d1", "d2", "d3"});

        // Nos subscribimos al mono
        mono.subscribe(monito_data -> {
            // onNext(): Se ejecuta por cada elemento emitido
            // por el Publisher. En un Mono, como máximo una vez.
            System.out.println(monito_data);
            elementFromMono.add(monito_data);
        }, error -> {
            // onError(): Se ejecuta cuando ocurre un error (Throwable).
            // Es una señal terminal que finaliza el flujo e impide
            // que posteriormente se ejecute onComplete().
            System.out.println(error.getMessage());
        }, () -> {
            // onComplete(): Señal terminal que indica que el flujo
            // ha finalizado correctamente, con o sin elementos.
            System.out.println("Flujo completado");
        });

        // Finally
        mono.doFinally(signal -> {
            // Se ejecuta cuando el flujo termina correctamente,
            // ocurre un error o se cancela la suscripción.
            System.out.println("Señal terminal: " + signal);
        }).subscribe();

        mono2.subscribe(data -> {
            System.out.println(data);
        }, error -> {
            // Como nuestro mono lo iniciamos con supplier, si es un error llegará aqui, en este caso solo tiene error
            System.out.println(error);
        });

        flux.subscribe(fluxito -> {
            elementFromFlux.add(fluxito);
        });

        System.out.println(elementFromMono);
        System.out.println(elementFromFlux);


        //flux2.subscribe(System.out::println);
        flux.doOnNext(data -> System.out.println(data)).subscribe();
    }


    public static void examples() {
        record Usuario(String nombre, String apellido) {
            @Override
            public String toString() {
                return "Usuario{nombre='%s', apellido='%s'}".formatted(nombre, apellido);
            }
        }

        Flux<String> nombres = Flux.just("Meisho Doto", "Satono Diamond", "Kitasan Black");
        Flux<Usuario> usuarios = nombres.map(nombre -> {
            String[] nom = nombre.split(" ");
            return new Usuario(nom[0].toUpperCase(), nom[1].toUpperCase());
        });
    }

}
