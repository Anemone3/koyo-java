package com.koyo.webflux;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Exercices {
    record Product(int id, String name, String category, int price, int stock, List<String> tags) {
    }

    private static final List<Product> PRODUCTS = List.of(new Product(1, "Laptop", "Tecnologia", 1200, 5, List.of("trabajo", "electronica")), new Product(2, "Mouse", "Tecnologia", 25, 0, List.of("accesorios", "electronica")), new Product(3, "Teclado", "Tecnologia", 80, 10, List.of("accesorios", "electronica")), new Product(4, "Monitor", "Tecnologia", 300, 3, List.of("trabajo", "electronica")), new Product(5, "Escritorio", "Muebles", 200, 2, List.of("oficina")), new Product(6, "Silla", "Muebles", 150, 0, List.of("oficina")), new Product(7, "Lampara", "Hogar", 40, 8, List.of("iluminacion", "oficina")), new Product(8, "Cuaderno", "Papeleria", 5, 20, List.of("estudio", "oficina")));

    public static void main(String[] args) {
        // Ejecuta tus ejercicios aquí.|
        crearSecuencias();
    }


    public static void crearSecuencias() {
        Flux<Product> fluxM = Flux.fromIterable(PRODUCTS);
        Flux<String> obtenerNombres = fluxM.map(Product::name);

        // Obtener productos
        obtenerNombres.subscribe(System.out::println);

        // Filtrar productos
        Flux<String> filterProductos = fluxM.filter(product -> product.stock > 0).map(Product::name);
        filterProductos.subscribe(System.out::println);

        // Aplicar varias condiciones
        var condicionesPrd = fluxM.filter(product -> product.stock > 0 && product.price <= 100).map(Product::name);
        condicionesPrd.subscribe(System.out::println);

        // Eliminar categorias repetidas
        var delCategoriasRepetidas = fluxM.map(Product::category).distinct();
        delCategoriasRepetidas.subscribe(System.out::println);

        // Extraer etiquetas
        var extraerEtiquetas = fluxM.flatMapIterable(Product::tags).distinct();
        extraerEtiquetas.subscribe(System.out::println);

        // Obtener los productos mas baratos
        var productosMasBaratos = fluxM.sort(Comparator.comparingInt(Product::price)).take(3).map(Product::name);
        productosMasBaratos.subscribe(System.out::println);

        // Paginar resultados
        int offset = 0, limit = 3;
        var paginatedproduct = fluxM.skip(offset).take(limit).map(Product::name);
        paginatedproduct.subscribe(System.out::println);
        offset += limit;
        paginatedproduct = fluxM.skip(offset).take(limit).map(Product::name);
        paginatedproduct.subscribe(System.out::println);

        // Contar productos
        var countProducts = fluxM.filter(product -> product.stock() == 0).count();

        countProducts.subscribe(System.out::println);

        // Acumular precios
        var acumularPrecios = fluxM.map(Product::price).reduce(0, (acc, currentValue) -> {
            return acc += currentValue;
        });

        acumularPrecios.subscribe(System.out::println);

        // Comparar una lista completa con varios lotes
        var unicaLista = fluxM.map(Product::id).collectList();
        var emiteTresListas = fluxM.map(Product::id).buffer(3);

        unicaLista.subscribe(System.out::println);
        emiteTresListas.subscribe(System.out::println);

        // Devolver un valor cuando no hay resultados
        int idToFound = 999;
        Mono<String> findingProduct = fluxM.filter(product -> product.id() == idToFound)
                .next() // Obtenemos el primero como Mono<Product>
                .map(Product::name)
                .defaultIfEmpty("No encontrado");

        findingProduct.subscribe(System.out::println);


        // example
        Map<Integer, Product> productsById = PRODUCTS.stream()
                .collect(Collectors.toMap(Product::id, product -> product));


        Function<Integer, Mono<Product>> findById = id ->
                // from Supplier
                Mono.fromSupplier(() -> {
                    System.out.println("Buscando producto: " + id);
                    return productsById.get(id);
                });
        // example


        // Consultar otra secuencia cuando no hay rsultados
        int idToFound2 = 999;
        int foundOther = 3;
        var foundOtherIfNotExists = fluxM.filter(product -> product.id() == idToFound2)
                .next()
                .switchIfEmpty(findById.apply(foundOther))
                .map(Product::name);
        foundOtherIfNotExists.subscribe(System.out::println);
    }
}
