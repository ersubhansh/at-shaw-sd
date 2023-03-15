package com.netcracker.shaw.element.pageor;
import static com.netcracker.shaw.factory.Locator.LINK_TEXT;
import static com.netcracker.shaw.factory.Locator.XPATH;

import org.openqa.selenium.By;

import com.netcracker.shaw.at_shaw_sd.util.Constants;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.element.PageElement;
import com.netcracker.shaw.factory.ByType;
import com.netcracker.shaw.factory.Locator;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public enum SOMOrderPageElement implements PageElement {
	
	SOM_ORDER_TAB(LINK_TEXT,getPropValue("somOrder")),
	ORDER_PARAMETER_TAB(XPATH,getPropValue("orderParameterTab")),
	INTEGRATION_REPORT(LINK_TEXT,getPropValue("integrationReport")),
	
	NEW_PHONE_PROVISIONING_RFS_ORDER1(XPATH,getPropValue("newPhoneProvisionRfsOrder1")),
	NEW_PHONE_PROVISIONING_RFS_ORDER2(XPATH,getPropValue("newPhoneProvisionRfsOrder2")),
	NEW_PHONE_PROVISIONING_RFS_ORDER3(XPATH,getPropValue("newPhoneProvisionRfsOrder3")),
	NEW_PHONE_PROVISIONING_RFS_ORDER4(XPATH,getPropValue("newPhoneProvisionRfsOrder4")),
	NEW_SOM_PHONE_LINE_CFS_ORDER(XPATH,getPropValue("newSomPhoneCfsOrder")),
	NEW_SOM_PHONE_LINE_RFS_ORDER(XPATH,getPropValue("newSomPhoneRfsOrder")),
	NEW_DISTINCTIVE_RING_RFS_ORDER(XPATH,getPropValue("newDistinctiveRingRFSOrder")),
	NEW_VOICEMAIL_PROVISIONING_RFS_ORDER1(XPATH,getPropValue("newSomVoicemailRfsOrder1")),
	
	NEW_INTERNET_PROVISIONING_RFS_ORDER1(XPATH,getPropValue("newInternetProvisionRfsOrder1")),
	NEW_INTERNET_PROVISIONING_RFS_ORDER2(XPATH,getPropValue("newInternetProvisionRfsOrder2")),
	NEW_INTERNET_PROVISIONING_RFS_ORDER3(XPATH,getPropValue("newInternetProvisionRfsOrder3")),
	NEW_EMAIL_PROVISIONING_RFS_ORDER(XPATH,getPropValue("newSOMEmailProvisioningRFSOrder")),
	NEW_EMAIL_PROVISIONING_RFS_ORDER_3(XPATH,getPropValue("newSOMEmailProvisioningRFSOrder3")),
	NEW_WIFI_PROVISIONING_RFS_ORDER(XPATH,getPropValue("newWifiProvisionRfsOrder")),
	
	NEW_TV_PROVISIONING_RFS_ORDER_1(XPATH,getPropValue("newTvProvisionRfsOrder1")),
	NEW_TV_PROVISIONING_RFS_ORDER_2(XPATH,getPropValue("newTvProvisionRfsOrder2")),
	NEW_TV_PROVISIONING_RFS_ORDER_3(XPATH,getPropValue("newTvProvisionRfsOrder3")),
	NEW_TV_PROVISIONING_RFS_ORDER_4(XPATH,getPropValue("newTvProvisionRfsOrder4")),
	NEW_TV_PROVISIONING_RFS_ORDER_5(XPATH,getPropValue("newTvProvisionRfsOrder5")),
	NEW_TV_PROVISIONING_RFS_ORDER_6(XPATH,getPropValue("newTvProvisionRfsOrder6")),
	NEW_TV_PROVISIONING_RFS_ORDER_9(XPATH,getPropValue("newTvProvisionRfsOrder9")),
	
	MODIFY_PHONE_PROVISIONING_RFS_ORDER(XPATH,getPropValue("modifyPhoneProvisionRfsOrder")),
	MODIFY_PHONE_PROVISIONING_RFS_ORDER2(XPATH,getPropValue("modifyPhoneProvisionRfsOrder2")),
	MODIFY_INTERNET_PROVISIONING_RFS_ORDER(XPATH,getPropValue("modifyInternetProvisionRfsOrder")),
	MODIFY_TV_PROVISING_RFS_ORDER1(XPATH,getPropValue("ModifyTVProvisingRFSOrder1")),
	MODIFY_TV_PROVISING_RFS_ORDER2(XPATH,getPropValue("ModifyTVProvisingRFSOrder2")),
	MODIFY_SOM_PHONELINE_BNSRFS_ORDER(XPATH,getPropValue("modifySOMPhoneLineBNSRFSOrder")),
	
	SUSPEND_TV_PROVISIONING_RFS_ORDER1(XPATH,getPropValue("suspendTvProvisionRfsOrder1")),
    SUSPEND_TV_PROVISIONING_RFS_ORDER2(XPATH,getPropValue("suspendTvProvisionRfsOrder2")),
    SUSPEND_PHONE_PROVISIONING_RFS_ORDER(XPATH,getPropValue("suspendPhoneProvisionRfsOrder")),
    SUSPEND_INTERNET_PROVISIONING_RFS_ORDER(XPATH,getPropValue("suspendInternetProvisionRfsOrder")),
	SUSPEND_EMAIL_PROVISIONING_RFS_ORDER(XPATH,getPropValue("suspendEmailProvisioningRFSOrder")),
	
	RESUME_PHONE_PROVISIONING_RFS_ORDER(XPATH,getPropValue("resumePhoneProvisionRfsOrder")),
	RESUME_INTERNET_PROVISIONING_RFS_ORDER(XPATH,getPropValue("resumeInternetProvisionRfsOrder")),
    RESUME_TV_PROVISIONING_RFS_ORDER1(XPATH,getPropValue("resumeTvProvisionRfsOrder1")),
    RESUME_TV_PROVISIONING_RFS_ORDER2(XPATH,getPropValue("resumeTvProvisionRfsOrder2")),
	RESUME_TV_PROVISIONING_RFS_ORDER3(XPATH,getPropValue("resumeTvProvisionRfsOrder3")),
	RESUME_TV_PROVISIONING_RFS_ORDER4(XPATH,getPropValue("resumeTvProvisionRfsOrder4")),
	
    DISCONNECT_PHONE_PROVISIONING_RFS_ORDER(XPATH,getPropValue("disconnectPhoneProvisionRfsOrder")),
	DISCONNECT_INTERNET_PROVISIONING_RFS_ORDER(XPATH,getPropValue("disconnectInternetProvisionRfsOrder")),
	DISCONNECT_INTERNET_PROVISIONING_RFS_ORDER2(XPATH,getPropValue("disconnectInternetProvisionRfsOrder2")),
	DISCONNECT_WIFI_PROVISIONING_RFS_ORDER(XPATH,getPropValue("disconnectWifiProvisionRfsOrder")),
	DISCONNECT_TV_PROVISIONING_RFS_ORDER1(XPATH,getPropValue("disconnectTVProvisionRfsOrder1")),
	DISCONNECT_TV_PROVISIONING_RFS_ORDER2(XPATH,getPropValue("disconnectTVProvisionRfsOrder2")),
	DISCONNECT_TV_HARDWARE_CFS_ORDER1(XPATH,getPropValue("disconnectSOMPTVCFSOrder1")),
	DISCONNECT_TV_HARDWARE_CFS_ORDER2(XPATH,getPropValue("disconnectSOMPTVCFSOrder2")),
	DISCONNECT_TV_HARDWARE_CFS_ORDER3(XPATH,getPropValue("disconnectSOMPTVCFSOrder3")),
	DISCONNECT_TV_HARDWARE_CFS_ORDER4(XPATH,getPropValue("disconnectSOMPTVCFSOrder4")),
	DISCONNECT_TV_HARDWARE_CFS_ORDER5(XPATH,getPropValue("disconnectSOMPTVCFSOrder5")),
	
	CANCEL_WIFI_PROVISIONING_RFS_ORDER(XPATH,getPropValue("cancelWifiProvisionRfsOrder")),
	CANCLE_TV_PROVISIONONG_ORDER(XPATH,getPropValue("cancelNewSOMTVProvisioningRFSOrder")),

	NON_PROVISIONABLE_HW_RFS_ORDER1(XPATH,getPropValue("newNonProvisionableHardwareRFSOrder1")),
	NON_PROVISIONABLE_HW_RFS_ORDER2(XPATH,getPropValue("newNonProvisionableHardwareRFSOrder2")),
	NON_PROVISIONABLE_HW_RFS_ORDER3(XPATH,getPropValue("newNonProvisionableHardwareRFSOrder3")),
	
	ROWS_IN_SOM_ORDER(XPATH,getPropValue("rowsInSomOrder")),
	NETCRACKER_TO_HPSA_JMS(XPATH,getPropValue("ncToHpsaJms")),
	SERVICE_INFORMATION_TAB(XPATH,getPropValue("serviceInformationTab")),
	SAVE_REPORT(LINK_TEXT,getPropValue("saveReport")),
	CUST_PHONE_NBR(XPATH,getPropValue("custPhoneNbr"));
	
	private Locator locator;
	private String expression;

	public static String getPropValue(String value){
        value = Utility.getValueFromPropertyFile( value, Constants.SOM_ORDER_PAGE_PROP_PATH);
        return value;
    }

	SOMOrderPageElement(Locator locator, String expression){
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
