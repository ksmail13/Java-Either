package util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * either like scala
 *
 * @author minkyu.kim
 */
public interface Either {

    /**
     * create left instance
     *
     * @param left left right
     * @param <L>  left type
     * @return left instance
     */
    static <L> Left left(L left) {
        return new Left<>(left);
    }

    /**
     * create right instance
     *
     * @param right result right
     * @param <R>   result type
     * @return result instance
     */
    static <R> Right right(R right) {
        return new Right<>(right);
    }

    /**
     * Save left result
     *
     * @param <L> left type
     */
    final class Left<L> implements Either {
        private final Optional<L> left;

        private Left(L left) {
            this.left = Optional.ofNullable(left);
        }

        @Override
        public Optional<L> left() {
            return left;
        }
    }

    /**
     * Save result
     *
     * @param <R> result type
     */
    final class Right<R> implements Either {

        private final Optional<R> right;

        private Right(R right) {
            this.right = Optional.ofNullable(right);
        }

        @Override
        public Optional<R> right() {
            return right;
        }
    }

    default <R> Optional<R> right() {
        return Optional.empty();
    }

    default <L> Optional<L> left() {
        return Optional.empty();
    }

    default boolean isLeft() {
        return this instanceof Left;
    }

    default boolean isRight() {
        return this instanceof Right;
    }


    default <R> Either flatMap(Function<R, Either> mappingFunction) {
        if (isRight()) {
            Optional<R> data = right();
            return data.flatMap(r -> Optional.ofNullable(mappingFunction.apply(r))).orElse(Either.left(null));
        } else {
            return left().flatMap(l -> Optional.of(Either.left(l))).orElse(Either.left(null));
        }
    }

    default <R, B> Either map(Function<R, B> mappingFunction) {
        return flatMap(r -> Either.right(mappingFunction.apply((R) r)));
    }

    default <R> void foreach(Consumer<R> body) {
        flatMap(r -> {
            body.accept((R) r);
            return null;
        });
    }

}
