package com.github.startsmercury.noshades.util;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Factory {
	public static interface ConstantSupplier {
		<T> Supplier<T> of(T constant);
	}

	public static interface Filter {
		<T, U> U of(T delegate, Function<T, U> constructor);
	}

	public static class SoftConstantSupplier implements ConstantSupplier {
		private SoftReference<Object> constant;

		private SoftReference<Supplier<Object>> supplier;

		@Override
		public <T> Supplier<T> of(final T constant) {
			if (this.constant.get() != constant) {
				this.constant = asSoftReference(constant);
			}

			if (this.supplier == null) {
				this.supplier = new SoftReference<>(() -> this.constant);
			}

			return (Supplier<T>) this.supplier.get();
		}
	}

	public static class SoftFilter implements Filter {
		private SoftReference<Object> delegate = NULL_SOFT_REFERENCE;

		private SoftReference<Object> filter = NULL_SOFT_REFERENCE;

		@Override
		public <T, U> U of(final T delegate, final Function<T, U> constructor) {
			if (this.delegate.get() != delegate) {
				this.delegate = asSoftReference(delegate);
				this.filter = asSoftReference(constructor.apply(delegate));
			}

			return (U) this.filter.get();
		}
	}

	public static class StrongConstantSupplier implements ConstantSupplier {
		private Object constant;

		private Supplier supplier;

		@Override
		public <T> Supplier<T> of(final T constant) {
			if (this.constant != constant) {
				this.constant = constant;
			}

			if (this.supplier == null) {
				this.supplier = () -> this.constant;
			}

			return this.supplier;
		}
	}

	public static class StrongFilter implements Filter {
		private Object delegate = null;

		private Object filter = null;

		@Override
		public <T, U> U of(final T delegate, final Function<T, U> constructor) {
			if (this.delegate != delegate) {
				this.delegate = delegate;
				this.filter = constructor.apply(delegate);
			}

			return (U) this.filter;
		}
	}

	public static class WeakConstantSupplier implements ConstantSupplier {
		private WeakReference<Object> constant;

		private WeakReference<Supplier<Object>> supplier;

		@Override
		public <T> Supplier<T> of(final T constant) {
			if (this.constant.get() != constant) {
				this.constant = asWeakReference(constant);
			}

			if (this.supplier == null) {
				this.supplier = new WeakReference<>(() -> this.constant);
			}

			return (Supplier<T>) this.supplier.get();
		}
	}

	public static class WeakFilter implements Filter {
		private WeakReference<Object> delegate = NULL_WEAK_REFERENCE;

		private WeakReference<Object> filter = NULL_WEAK_REFERENCE;

		@Override
		public <T, U> U of(final T delegate, final Function<T, U> constructor) {
			if (this.delegate.get() != delegate) {
				this.delegate = asWeakReference(delegate);
				this.filter = asWeakReference(constructor.apply(delegate));
			}

			return (U) this.filter.get();
		}
	}

	private static final SoftReference NULL_SOFT_REFERENCE = new SoftReference(null);

	private static final WeakReference NULL_WEAK_REFERENCE = new WeakReference(null);

	protected static SoftReference<Object> asSoftReference(final Object x) {
		return x != null ? new SoftReference<>(x) : NULL_SOFT_REFERENCE;
	}

	protected static WeakReference<Object> asWeakReference(final Object x) {
		return x != null ? new WeakReference<>(x) : NULL_WEAK_REFERENCE;
	}

	protected Factory() {
	}
}
