package jhaturanga.model.game.gametypes;

@FunctionalInterface
interface TriFunction<A, B, C, R> {

    R apply(A a, B b, C c);

}
