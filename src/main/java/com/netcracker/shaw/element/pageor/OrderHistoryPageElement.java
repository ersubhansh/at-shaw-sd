package com.netcracker.shaw.element.pageor;

import com.netcracker.shaw.element.PageElement;
import org.openqa.selenium.By;

import com.netcracker.shaw.at_shaw_sd.util.Constants;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.factory.ByType;
import com.netcracker.shaw.factory.Locator;
import static com.netcracker.shaw.factory.Locator.*;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public enum OrderHistoryPageElement implements PageElement {
	
	WORK_ORDER_LINK(XPATH,getPropValue("workOrderLink")),
	CANCELLED_ORDER(XPATH,getPropValue("cancelledOrder")),
	RETAIL_PICKUP_MAILOUT(XPATH,getPropValue("retailPickUpMailOut")),
	PRODUCT_SUMMARY(XPATH,getPropValue("productSummary")),
	AGREEMENT_MANAGER(XPATH,getPropValue("agreementManager")),
	ORDER_STATUS_ACTIVATION_BTN(XPATH,getPropValue("orderStatusActivateBtn")),
	CONFIRM_ACTIVATION_BTN(XPATH,getPropValue("confirmActivationBtn")),
	HARDWARE_FOR_RETAIL_PICKUP(XPATH,getPropValue("hardwareForRetailPickup")),
	ADDITIONAL_INFORMATION_VIEW_MORE(XPATH,getPropValue("additionalInformationViewMore")),
	PHONE_INPUT(XPATH,getPropValue("phoneInput")),
	INTERNET_INPUT(XPATH,getPropValue("internetInput")),
	XB6_CONVERGED_INPUT(XPATH,getPropValue("xb6convergedInput")),
	TVBOX_INPUT(XPATH,getPropValue("tvBoxInput")),
	TVPORTAL_INPUT(XPATH,getPropValue("tvPortalInput")),
	DCX_TV_INPUT(XPATH,getPropValue("dcxTvInput")),
	SHAW_BLUESKY_TVBOX_INPUT(XPATH,getPropValue("shawBlueSkyTVboxInput")),
	SHAW_BLUESKY_TVPORTAL_INPUT(XPATH,getPropValue("shawBlueSkyTVportalInput")),
	DCX3200M_506_501_INPUT(XPATH,getPropValue("DCX3200M(506/501)Input")),
	DCX3200_HDGUIDE_518_517_INPUT(XPATH,getPropValue("DCX3200HDGuide(518/517/516/511)")),
	DCX3200P2MTC_INPUT(XPATH,getPropValue("DCX3200P2MTC(520/519)")),
	DCT700_599_594_INPUT(XPATH,getPropValue("DCT700(599/594)")),
	DCX3400_250GB_INPUT(XPATH,getPropValue("DCX3400(250GB)(507/502)")),
	DCX3400_500GB_INPUT(XPATH,getPropValue("DCX3400(500GB)(505/500)")),
	
	SUBMIT_SERIAL_NUMBERS_BTN(XPATH,getPropValue("submitSerialNumbersBtn")),
	SUBMIT_SRLNBRS_DISABLE_BTN(XPATH,getPropValue("submitSrlNbrsDisableBtn")),
	HARDWARE_RESET(XPATH,getPropValue("hardwareReset"));
	
	private Locator locator;
	private String expression;
	
	public static String getPropValue(String value){
        value = Utility.getValueFromPropertyFile( value, Constants.ORDER_HISTORY_PAGE_PROP_PATH);
        return value;
    }
	
	OrderHistoryPageElement(Locator locator, String expression){
		this.locator = locator;
		this.expression = expression;
	}
	
	public Locator getLocator() {
		return locator;
	}

	public String getValue() {
		return expression;
	}

	public By getBy(String...placeholder) {
		return ByType.getLocator(locator, expression, placeholder);
	}
}
