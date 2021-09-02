package com.github.startsmercury.noshades;

import java.util.function.Predicate;

public enum ShadeOverride implements Predicate<Boolean> {
	OFF {
		@Override
		public boolean reshade(boolean x) {
			return false;
		}
	},
	DEFAULT {
		@Override
		public boolean reshade(boolean x) {
			return x;
		}
	},
	ON {
		@Override
		public boolean reshade(boolean x) {
			return true;
		}
	};

	public static ShadeOverride compose(ShadeOverride second, ShadeOverride first) {
		return DEFAULT != second ? second : first;
	}

	public static ShadeOverride inverse(ShadeOverride shade) {
		return values()[2 - shade.ordinal()];
	}

	public abstract boolean reshade(boolean shade);

	@Override
	public boolean test(Boolean shade) {
		return reshade(Boolean.TRUE.equals(shade));
	}
}
