package com.future.bbetter.schedule.constant;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;

import org.junit.Test;

public class CYCLE_RULETest {

	/***
	 * test validatePeriod() method,
	 * 給予正確的值應得到正確的結果
	 */
	@Test
	public void whenValidatePeriodAndLegalValue_thenSuccess() {
		//given
		IntStream values = IntStream.of(1,4,2,3);

		//when
		boolean result = values
				.mapToObj(i -> CYCLE_RULE.validatePeriod(i))
				.allMatch(b -> b);
		
		//then
		assertThat(result).isEqualTo(true);
	}
	
	
	/***
	 * test validatePeriod() method,
	 * 給予不合法的值應回傳false
	 */
	@Test
	public void whenValidatePeriodAndIllegalValue_thenFail() {
		//given
		IntStream values = IntStream.of(0,-1,-99,99,10,5);

		//when
		boolean result = values
				.mapToObj(i -> CYCLE_RULE.validatePeriod(i))
				//將回傳的結果收集起來
				.allMatch(b -> b);
//				.allMatch(b -> !b); //亦可這樣寫則需配合下方的驗證<2>
		
		//then
		assertThat(result).isEqualTo(false);
//		assertThat(result).isEqualTo(true); //驗證<2>, 需配合上方的第二種收集
	}
	
}
