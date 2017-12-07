package util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.xml.crypto.Data;

/**
 * either like scala
 * 
 * @author minkyu.kim
 *
 * @param <L> Left (error type)
 * @param <R> Right (result type)
 */
public interface Either<L, R> {
	
	static <L, R> Left<L, R> left(L left) {
		return new Left<>(left);
	}
	
	static <L, R> Right<L, R> right(R right) {
		return new Right<>(right);
	}
	
	class Left<L, R> implements Either<L, Object> {
		private final Optional<L> left;
		private Left(L left) {
			this.left = Optional.ofNullable(left);
		}
		
		@Override
		public Optional<L> error() {
			return left;
		}
	}
	
	
	class Right<L, R> implements Either<L, R> {
		
		private final Optional<R> right;
		private Right(R right) {
			this.right = Optional.ofNullable(right);
		}
		
		@Override
		public Optional<R> data() {
			return right;
		}

	}
	
	default Optional<R> data() {
		return Optional.empty();
	}
	
	default Optional<L> error() {
		return Optional.empty();	
	}
	
	default boolean isLeft() {
		return this instanceof Left;
	}
	
	default boolean isRight() {
		return this instanceof Right;
	}
	
	
	default <B> Either<L, B> flatMap(Function<R, Either<L, B>> mappingFunction) {
		return null;
	}

	default <B> Either<L, B> map(Function<R, B> mappingFunction) {
		if(isRight()) {
			return flatMap(r -> Either.right(mappingFunction.apply(r)));
		} else {
		    return error().flatMap(l -> Optional.of(Either.left(l))).orElse(Either.left(null));
        }
	}
	
	default void foreach(Consumer<R> body) {
		if(isRight()) {
			
		}
	}

}
