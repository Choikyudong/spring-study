package com.example.springjpa;

import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TeestExecutionListener implements BeforeTestExecutionCallback {

	@Override
	public void beforeTestExecution(ExtensionContext context) {
		String TEST_NAME = """
					*********************************************
					Current Test is %s
					*********************************************
				""";
		System.out.printf("\n" + TEST_NAME + "\n", context.getDisplayName());
	}

}
