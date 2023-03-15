package com.netcracker.shaw.element.pageor;

import org.openqa.selenium.By;
import com.netcracker.shaw.at_shaw_sd.util.Constants;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.element.PageElement;
import com.netcracker.shaw.factory.ByType;
import com.netcracker.shaw.factory.Locator;
import static com.netcracker.shaw.factory.Locator.*;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public enum COMOrdersPageElement implements PageElement {
	ACCOUNT_INFORMATION_TAB(LINK_TEXT,getPropValue("AccountInformation")),
	COM_ORDER_TAB(LINK_TEXT,getPropValue("comOrder")),
	CLEC_REQUEST_TAB(LINK_TEXT,getPropValue("clecRequests")),
	
	NEW_CUSTOMER_ORDER(XPATH,getPropValue("newCustOrder")),
	NEW_CUSTOMER_ORDER_2(XPATH,getPropValue("newCustOrder2")),
	NEW_CUSTOMER_ORDER_3(XPATH,getPropValue("newCustOrder3")),
	NEW_CUSTOMER_ORDER_5(XPATH,getPropValue("newCustOrder5")),
	NEW_CUSTOMER_ORDER_STATUS(XPATH,getPropValue("newCustOrderStatus")),
	
	MODIFY_CUSTOMER_ORDER1(XPATH,getPropValue("modifyCustOrder1")),
	MODIFY_CUSTOMER_ORDER2(XPATH,getPropValue("modifyCustOrder2")),
	MODIFY_CUSTOMER_ORDER3(XPATH,getPropValue("modifyCustOrder3")),
	MODIFY_CUSTOMER_ORDER4(XPATH,getPropValue("modifyCustOrder4")),
	
	INFLIGHT_NEW_CUST_ORDER(XPATH,getPropValue("inFlightNewCustOrder")),
	INFLIGHT_NEW_CUST_ORDER2(XPATH,getPropValue("inFlightNewCustOrder2")),
	INFLIGHT_NEW_CUST_ORDER3(XPATH,getPropValue("inFlightNewCustOrder3")),
	
	DISCONNECT_CUSTOMER_ORDER(XPATH,getPropValue("disconnectCustomerOrder")),
	
	NEW_PHONELINE_CFS_ORDER1(XPATH,getPropValue("newPhoneLineCfsOrder1")),
	NEW_PHONELINE_CFS_ORDER2(XPATH,getPropValue("newPhoneLineCfsOrder2")),
	NEW_PHONELINE_PRODUCT_ORDER(XPATH,getPropValue("newPhoneLineProdOrder")),
	NEW_PHONE_HARDWARE_CFS_ORDER1(XPATH,getPropValue("newPhoneHardwareCFSOrder1")),
	NEW_PHONE_HARDWARE_CFS_ORDER2(XPATH,getPropValue("newPhoneHardwareCFSOrder2")),
	NEW_PHONE_HARDWARE_CFS_ORDER3(XPATH,getPropValue("newPhoneHardwareCFSOrder3")),
	NEW_PHONE_HARDWARE_CFS_ORDER7(XPATH,getPropValue("newPhoneHardwareCFSOrder7")),
	NEW_VOICEMAIL_CFS_ORDER(XPATH,getPropValue("newVoiceMailCfsOrder")),
	NEW_DISTINCTIVE_RING_CFS_ORDER(XPATH,getPropValue("newDistinctiveRingCfsOrder")),
	PHONE_LINE_IMMEDIATE_ORDER(XPATH,getPropValue("phoneLineImmdOrder")),
	NEW_PHONELINE_CFS_ORDER_STATUS(XPATH,getPropValue("newPhoneLineCfsOrderStatus")),
	NEW_PHONELINE_PRODUCT_ORDER_STATUS(XPATH,getPropValue("newPhoneLineProdOrderStatus")),
	NEW_VOICEMAIL_CFS_ORDER_STATUS(XPATH,getPropValue("newVoiceMailCfsOrderStatus")),
	NEW_CDI_CFS_ORDER(XPATH,getPropValue("newCdiCfsOrder")),
	
	MODIFY_PHONE_HARDWARE_CFS_ORDER1(XPATH,getPropValue("modifyPhoneHardwareCFSOrder1")),
    MODIFY_PHONE_HARDWARE_CFS_ORDER2(XPATH,getPropValue("modifyPhoneHardwareCFSOrder2")),
    MODIFY_PHONE_HARDWARE_CFS_ORDER3(XPATH,getPropValue("modifyPhoneHardwareCFSOrder3")),
	
	NEW_INTERNET_CFS_ORDER(XPATH,getPropValue("newInternetCfsOrder")),
	NEW_INTERNET_HARDWARE_CFS_ORDER1(XPATH,getPropValue("newInternetHardwareCfsOrder1")),
	NEW_INTERNET_HARDWARE_CFS_ORDER2(XPATH,getPropValue("newInternetHardwareCfsOrder2")),
	NEW_INTERNET_HARDWARE_CFS_ORDER3(XPATH,getPropValue("newInternetHardwareCfsOrder3")),
	NEW_INTERNET_CFS_ORDER_STATUS(XPATH,getPropValue("newInternetCfsOrderStatus")),
	NEW_INTERNET_PRODUCT_ORDER_STATUS(XPATH,getPropValue("newInternetProdOrderStatus")),
	NEW_INTERNET_PRODUCT_ORDER(XPATH,getPropValue("newInternetProductOrder")),
	NEW_CONVERGED_HARDWARE_CFS_ORDER(XPATH,getPropValue("newConvergedHardwareCfsOrder")),
	NEW_CONVERGED_HARDWARE_CFS_ORDER2(XPATH,getPropValue("newConvergedHardwareCfsOrder2")),
	NEWINTERNETPRODUCTORDER(XPATH,getPropValue("newInternetProdOrder")),
	NEW_CONVERGED_HARDWARE_CFS_ORDER_STATUS(XPATH,getPropValue("newConvergedHardwareCfsOrderStatus")),
	NEW_WIFI_CFS_ORDER(XPATH,getPropValue("newWifiCfsOrder")),
	NEW_CDI_CFS_ORDER_STATUS(XPATH,getPropValue("newCdiCfsOrderStatus")),
	NEW_WIFI_CFS_ORDER_STATUS(XPATH,getPropValue("newWifiCfsOrderStatus")),
	
	MODIFY_INTERNET_HARDWARE_CFS_ORDER1(XPATH,getPropValue("modifyInternetHardwareCFSOrder1")),
    MODIFY_INTERNET_HARDWARE_CFS_ORDER2(XPATH,getPropValue("modifyInternetHardwareCFSOrder2")),
    MODIFY_INTERNET_HARDWARE_CFS_ORDER3(XPATH,getPropValue("modifyInternetHardwareCFSOrder3")),
    MODIFY_CONVERGRED_HARDWARE_CFS_ORDER_LINK(XPATH,getPropValue("modifyConvergedHardwareCfsOrder")),
    NEW_NON_PROVISIONABLE_HARWARE_ORDER1(XPATH,getPropValue("nonProvisionableOrder1")),
	NEW_NON_PROVISIONABLE_HARWARE_ORDER2(XPATH,getPropValue("nonProvisionableOrder2")),
	NEW_NON_PROVISIONABLE_HARWARE_ORDER3(XPATH,getPropValue("nonProvisionableOrder3")),    
	MODIFY_INTERNET_CFS_ORDER(XPATH,getPropValue("modifyInternetCfsOrder")),
	
	NEW_TV_CFS_ORDER(XPATH,getPropValue("newTvCfsOrder")),
	NEW_TV_PRODUCT_ORDER(XPATH,getPropValue("newTvProdOrder")),
	NEW_VODP_PARAMS_CFSO_ORDER(XPATH,getPropValue("newVodParamsCfsOrder")),
	NEW_TV_HARDWARE_CFS_ORDER_STATUS(XPATH,getPropValue("newTvHardwareCfsOrderStatus")),
    NEW_TV_HARDWARE_CFS_ORDER1(XPATH,getPropValue("newTvHardwareCfsOrder1")),
    NEW_TV_HARDWARE_CFS_ORDER2(XPATH,getPropValue("newTvHardwareCfsOrder2")),
    NEW_TV_HARDWARE_CFS_ORDER3(XPATH,getPropValue("newTvHardwareCfsOrder3")),
    NEW_TV_HARDWARE_CFS_ORDER4(XPATH,getPropValue("newTvHardwareCfsOrder4")),
    NEW_TV_HARDWARE_CFS_ORDER5(XPATH,getPropValue("newTvHardwareCfsOrder5")),
	NEW_TV_CFS_ORDER_STATUS(XPATH,getPropValue("newTvCfsOrderStatus")),
	NEW_TV_PRODUCT_ORDER_STATUS(XPATH,getPropValue("newTvProdOrderStatus")),
	NEW_VOD_PARAMS_CFS_ORDER_STATUS(XPATH,getPropValue("newVodParamsCfsOrderStatus")),
	
	MODIFY_TV_HARDWARE_CFS_ORDER_1(XPATH,getPropValue("modifyTvHardwareCfsOrder1")),
    MODIFY_TV_HARDWARE_CFS_ORDER_2(XPATH,getPropValue("modifyTvHardwareCfsOrder2")),
    MODIFY_TV_HARDWARE_CFS_ORDER_3(XPATH,getPropValue("modifyTvHardwareCfsOrder3")),
	MODIFY_TV_CFS_ORDER(XPATH,getPropValue("modifyTVCfsOrder")),
	MODIFY_TV_PRODUCT_ORDER(XPATH,getPropValue("modifyTVProductOrder")),
	MODIFY_VOD_CFS_ORDER(XPATH,getPropValue("modifyVODCfsOrder")),
	
	SUSPEND_TV_HARDWARE_CFS_IMMEDIATE_ORDER(XPATH,getPropValue("suspendTvHardwareCfsImmdOrder")),
	SUSPEND_TV_PRODUCT_IMMEDIATE_ORDER(XPATH,getPropValue("suspendTvProdImmdOrder")),
	SUSPEND_EMAIL_CFS_ORDER(XPATH,getPropValue("suspendEmailCfsOrder")),
	SUSPESNTION_TV_HISTORY_RECORD1(XPATH,getPropValue("suspensionTVHistoryRecord1")),
	SUSPESNTION_TV_HISTORY_RECORD2(XPATH,getPropValue("suspensionTVHistoryRecord2")),
	SUSPEND_TV_HARDWARE_CFS_IMMEDIATE_ORDER_STATUS(XPATH,getPropValue("suspendTvHardwareCfsImmdOrderStatus")),
	SUSPEND_TV_PRODUCT_IMMEDIATE_ORDER_STATUS(XPATH,getPropValue("suspendTvProdImmdOrderStatus")),
	SUSPESNTION_TV_HARDWARE_STATUS(XPATH,getPropValue("suspensionTVHardwareStatus")),
    SUSPESNTION_INTERNET_HARDWARE_STATUS(XPATH,getPropValue("suspensionInternetHardwareStatus")),
    SUSPESNTION_PHONE_HARDWARE_STATUS(XPATH,getPropValue("suspensionPhoneHardwareStatus")),
    SUSPESNTION_PHONE_HISTORY_RECORD(XPATH,getPropValue("suspensionPhoneHistoryRecord")),
    SUSPESNTION_INTERNET_HISTORY_RECORD(XPATH,getPropValue("suspensionInternetHistoryRecord")),
    SUSPESNTION_TV_HISTORY_RECORD(XPATH,getPropValue("suspensionTVHistoryRecord")),
	ROOT_SUSPEND_IMMEDIATE_ORDER_STATUS(XPATH,getPropValue("rootSuspendImmdOrderStatus")),
	ROOT_SUSPEND_IMMEDIATE_ORDER(XPATH,getPropValue("rootSuspendImmdOrder")),
	
	RESUME_TV_HARWARE_CFS_IMMEDIATE_ORDER(XPATH,getPropValue("resumeTvHardwareCfsImmdOrder")),
	RESUME_TV_PRODUCT_IMMEDIATE_ORDER(XPATH,getPropValue("resumeTvProdImmdOrder")),
	RESUME_TV_HARWARE_CFS_IMMEDIATE_ORDER_STATUS(XPATH,getPropValue("resumeTvHardwareCfsImmdOrderStatus")),
	RESUME_TV_PRODUCT_IMMEDIATE_ORDER_STATUS(XPATH,getPropValue("resumeTvProdImmdOrderStatus")),
	ROOT_RESUME_IMMEDIATE_ORDER_STATUS(XPATH,getPropValue("rootResumeImmdOrderStatus")),
	ROOT_RESUME_IMMEDIATE_ORDER(XPATH,getPropValue("rootResumeImmdOrder")),
	
	NEW_BILLING_SERVICE_ORDER(XPATH,getPropValue("newBillingSvcOrder")),
	NEW_BILLING_SERVICE_ORDER_2(XPATH,getPropValue("newBillingSvcOrder2")),
	NEW_BILLING_SERVICE_ORDER_3(XPATH,getPropValue("newBillingSvcOrder3")),
	NEW_BILLING_SERVICE_ORDER_5(XPATH,getPropValue("newBillingSvcOrder5")),
	NEW_BILLING_SERVICE_ORDER_STATUS(XPATH,getPropValue("newBillingSvcOrderStatus")),
	
	MODIFY_BILLING_SERVICE_ORDER1(XPATH,getPropValue("modifyBillingServiceOrder1")),
	MODIFY_BILLING_SERVICE_ORDER2(XPATH,getPropValue("modifyBillingServiceOrder2")),
	MODIFY_BILLING_SERVICE_ORDER3(XPATH,getPropValue("modifyBillingServiceOrder3")),
	
	DISCONNECT_BILLING_SERVICE_ORDER(XPATH,getPropValue("disconnectBillingOrder")),
	
	INFLIGHT_NEW_CONV_HW_CFS_ORDER(XPATH,getPropValue("inFlightNewConvHwCFSOrder")),
	INFLIGHT_NEW_INT_HW_CFS_ORDER(XPATH,getPropValue("inFlightNewInternetHwCFSOrder1")),
	INFLIGHT_MODIFY_CUST_ORDER(XPATH,getPropValue("inFlightModifyCustOrder")),
	INFLIGHT_NEW_BILLING_SERVICE_ORDER(XPATH,getPropValue("inFlightNewBillingOrder")),
	INFLIGHT_NEW_BILLING_SERVICE_ORDER3(XPATH,getPropValue("inFlightNewBillingOrder3")),
	INFLIGHT_MODIFY_BILLING_ORDER(XPATH,getPropValue("inFlightModifyBillingOrder")),
	
	ADD_EMAIL_IMMEDIATE_ORDER(XPATH,getPropValue("addEmailImmediateOrder")),
	SWAP_HARDWAREIMMEDIATE_ORDER(XPATH,getPropValue("swapHardwareImmediateOrder")),
	IMMEDIATE_TAB_HARDWARE(XPATH,getPropValue("immediateTabHardware")),
	IMMEDIATE_CONVRGD_SWAP1(XPATH,getPropValue("immediateConvrgdSwap1")),
	IMMEDIATE_CONVRGD_SWAP2(XPATH,getPropValue("immediateConvrgdSwap2")),
	CANCEL_NEW_CUSTOMER_ORDER(XPATH,getPropValue("cancelNewCustOrder")),
	
	CHARGE_HARDWARE_REQUEST_FOR_SN1(XPATH,getPropValue("chargeHardwareRequestForSN1")),
	CHARGE_HARDWARE_REQUEST_FOR_SN2(XPATH,getPropValue("chargeHardwareRequestForSN2")),
	CHARGE_HARDWARE_REQUEST_FOR_SN3(XPATH,getPropValue("chargeHardwareRequestForSN3")),
	CHARGE_HARDWARE_REQUEST_FOR_SN4(XPATH,getPropValue("chargeHardwareRequestForSN4")),
	
    BLIF_REQUEST(LINK_TEXT,getPropValue("blifRequest")),
    E911ADD(LINK_TEXT,getPropValue("e911Add")),
    BLIF_REQUEST_STATUS(XPATH,getPropValue("bliffRequestStatus")),
    E911ADD_STATUS(XPATH,getPropValue("e911AddStatus")),
    E911ADD_STATUS2(XPATH,getPropValue("e911AddStatus2")),
    SUSPENSION_STATUS(XPATH,getPropValue("suspensionStatus")),
	E911_REQUEST_STATUS(XPATH,getPropValue("E911RequestStatus")),
    BLIF_REQUEST_STATUS1(XPATH,getPropValue("bliffRequestStatus1")),
    BLIF_REQUEST_STATUS2(XPATH,getPropValue("bliffRequestStatus2")),    
    LSR_ALLSTRREAM_P_STATUS1(XPATH,getPropValue("LsrAllstreamPStatus1")),
    LSR_AllSTRREAM_P_STATUS2(XPATH,getPropValue("LsrAllstreamPStatus2")),
    NPAC_REQUEST_STATUS1(XPATH,getPropValue("NPACRequestStatus1")),
    NPAC_REQUEST_STATUS2(XPATH,getPropValue("NPACRequestStatus2")),
    SUSPEND_BUTTON(LINK_TEXT,getPropValue("suspendButton")),
    SUSPEND_INTERNET(XPATH,getPropValue("suspendInternet")),
    SUSPEND_TV(XPATH,getPropValue("suspendTv")),
    SUSPEND_PHONE(XPATH,getPropValue("suspendPhone")),
    SUSPENSION_NOTES(XPATH,getPropValue("suspensionNotes")),
    AUTHORIZED_BY(XPATH,getPropValue("authorizedBy")),
    SUBMIT_BUTTON(XPATH,getPropValue("submitButton")),
    RETURN_TO_SUSPENSION_HISTORY(XPATH,getPropValue("returnToSuspensionHist")),
    SUSPENSION_HISTORY_RECORD(LINK_TEXT,getPropValue("suspensionHistoryRecord")),
    RESUME_BUTTON(LINK_TEXT,getPropValue("resumeButton")),
    RETURN_TO_SUSPENSION_HISTORY_RECORD(LINK_TEXT,getPropValue("returnToSuspensionHistRecord")),
    RESUME_NOTE(XPATH,getPropValue("resumeNote")),
    RESUME_AUTHORIZED(XPATH,getPropValue("resumeAuthorized")),
    LINK_TO_COM_PAGE(XPATH,getPropValue("linkToComPage")),
	E911_RESPONSE(XPATH,getPropValue("e911Response")),
	CLEC_TAB_TABLE(XPATH,getPropValue("clecTabTable")),
	SVG_DIAGRAM(LINK_TEXT,getPropValue("svgDiagram")),
	TASK_TAB(LINK_TEXT,getPropValue("taskTab")),
	TASK_FILTER_STATUS(XPATH,getPropValue("taskFilterStatus")),
	TASK_WAITING_CHECKBOX(LINK_TEXT,getPropValue("taskWaitingCheckBox")),
	TASK_WAITING_EFFECTIVEDATE(XPATH,getPropValue("taskWaiting")),
	TASK_ERROR_CHECKBOX(LINK_TEXT,getPropValue("taskErrorCheckBox")),
	TASK_SELECT_ALL(XPATH,getPropValue("taskSelectAll")),
	MARK_FINISHED(XPATH,getPropValue("markFinished")),
	TASK_FORCE_RETRY(XPATH,getPropValue("taskForceRetry")),
	TASK_SEND_SERIAL_NBR(LINK_TEXT,getPropValue("taskSendSerialNbr")),
	TASK_TECHNICIAN_ID(ID,getPropValue("taskTechnicianId")),
	TASK_SERIAL_NBR(ID,getPropValue("taskSerialNbr")),
	TASK_SEND_BUTTON(ID,getPropValue("taskSendButton")),
	GENERATE_STORE_CONTRACT_STATUS(XPATH,getPropValue("generateStoreContractStatus")),
	TASK_OK_BUTTON(ID,getPropValue("taskOkButton")),
	ORDER_PARAMETERS(XPATH,getPropValue("orderParameters")),
	OLD_SERIAL_NBR(XPATH,getPropValue("oldSerialNbr")),
	PROVIONING_REGISTRATION_KEY(XPATH,getPropValue("provisioningRegistrationKey")),
	SERVICE_INFORMATION(LINK_TEXT,getPropValue("serviceInfo")),
	FALSE_TEXT(XPATH,getPropValue("falsetext")),
	COM_ACCT_NBR(XPATH,getPropValue("comAcctNbr")),
	ORDER_STATUS(XPATH,getPropValue("orderStatus")),
	OFFERING_ID(XPATH,getPropValue("offeringID")),
    EFFECTIVE_DATE_STATUS(XPATH,getPropValue("effectiveDateStatus")),
    SERVICE_INFORMATION_TAB(XPATH,getPropValue("serviceInforTab")),
    PORT_OPTION(XPATH,getPropValue("portOption")),
    BLIF_REQUEST_LINK1(XPATH,getPropValue("bliffRequestLink1")),
    BLIF_REQUEST_LINK2(XPATH,getPropValue("bliffRequestLink2")),
    LSR_ALLSTREAM_P1_LINK1(XPATH,getPropValue("lsrAllstreamPLink1")),
    LSR_ALLSTREAM_P1_LINK2(XPATH,getPropValue("lsrAllstreamPLink2")),
    NPAC_REQUEST_LINK1(XPATH,getPropValue("npscRequestLink1")),
    NPAC_REQUEST_LINK2(XPATH,getPropValue("npscRequestLink2")),  
    E911_REQUEST_LINK(XPATH,getPropValue("e911RequestLink")),
    LSC_RESPONSE_JOB(XPATH,getPropValue("lscResponseJob")),
    BLIF_RESPONSE_JOB(XPATH,getPropValue("blifResponseJob")),
    DISCONNECT_RETURN_HW_JOB(XPATH,getPropValue("disconnectReturnHardware")),
	RUN_JOB_BUTTON(LINK_TEXT,"Run Job"),
	MARK_FINISH_DESCRIPTION(XPATH,getPropValue("markFininshDescription")),
	MARK_FINISH_DESCRIPTION_SEND_BTN(XPATH,getPropValue("markFininshDescriptionSendBtn")),
	PORT_BLIF_REQUEST_STATUS(XPATH,getPropValue("portBliffRequestStatus")),
	E911_REQUEST_STATUS_PORT(XPATH,getPropValue("E911RequestStatusPort")),
	CUSTOMER_PORTAL_TAB(XPATH,getPropValue("customerPortalTab")),
	CUSTOMER_QUOTE_SELECTION(XPATH,getPropValue("customerQuoteSelection")),
	QUOTE_DISCOUNTS(XPATH,getPropValue("quoteDiscounts")),
	FIBRE150_ACTIVE_CONTRACTS(XPATH,getPropValue("fibre150ActiveContract")),
	FIBRE150_CONTRACTS_STATUS(XPATH,getPropValue("fibre150ContractStatus")),
	METHOD_OF_CONFIRMATION_STATUS(XPATH,getPropValue("methodOfConfirmationStatus")),
    ACCEPTANCE_ID_STATUS(XPATH,getPropValue("acceptanceIDStatus")),
	TV_SERIAL_NBRS_GET_TEXT(XPATH,getPropValue("tvSerialNbrsGetText")),
	ORDER_NUMBER_LINK(XPATH,getPropValue("orderNumberLink")),
	SUSPEND_DIGITAL_PHONE(XPATH,getPropValue("suspendDigitalPhone")),
    SUSPEND_INTERNET_TEXT(XPATH,getPropValue("suspendInternetText")),
    NEW_CLEC_CFS_ORDER(XPATH,getPropValue("newClecCfsOrder")),
    SHIPPING_HW_DROPDOWN(XPATH,getPropValue("shippinghwDropdown")),
    SHIPPING_HW_VALUE(XPATH,getPropValue("shippinghwValue")),
    SHIPPING_TASKS_SELECT(XPATH,getPropValue("shippingTasksSelect")),  
    START_SHIPPING_LINK(XPATH,getPropValue("startShippingLink")), 
    SRLNBR_MOUSE_OVER(XPATH,getPropValue("SrlnbrMouseOver")), 
    SRL_NBR_DROPDOWN(XPATH,getPropValue("srlNbrDropDown")),
    SRL_NBR_INPUT(XPATH,getPropValue("srlNbrInput")),
    HARDWARE_MOUSE_OVER(XPATH,getPropValue("hardwareMouseOver")),  
    TRACKING_NUMBER(XPATH,getPropValue("trackingNumber")),  
    SAVE_LINK(XPATH,getPropValue("saveLink")), 
    COMPLETE_SHIPPING(XPATH,getPropValue("completeShipping")), 
    TRACKING_INPUT_VALUE(XPATH,getPropValue("trackingInput")),
    LSR_ORDER1(XPATH,getPropValue("lsrOrderLink1")),
	LSR_ORDER2(XPATH,getPropValue("lsrOrderLink2")),
	LSR_ORDER3(XPATH,getPropValue("lsrOrderLink3")),
	WAIT_FOR_ALL_LSCS_LINK(XPATH,getPropValue("waitForAllLSCsLink")),
	WAIT_FOR_ALL_LSCS_TASK(XPATH,getPropValue("waitForAllLSCsTask")),
	STEP_OVER_LINK(XPATH,getPropValue("stepOverLink")),
	WAIT_FOR_LSC_FILTER(XPATH,getPropValue("waitForLSCFilter")),
	PORT_IN_IMMEDIATELY_YES(XPATH,getPropValue("portInImmediatelyYes")),
	EMAIL_ADDRESS(XPATH,getPropValue("immediateEmailAddress")),
	WORK_ORDERS(LINK_TEXT,getPropValue("workOrders")),
	CANCELLED_ORDER(XPATH,getPropValue("cancelledOrder")),
	HARDWARE_TYPE(XPATH,getPropValue("hardwareType")),
	MOXI_HARDWARE_TYPE(XPATH,getPropValue("moxiHardwareType")),
	ERRORS(LINK_TEXT,getPropValue("errors")),
	SUBMIT_SRLNBRS_DISABLE_BTN(XPATH,getPropValue("submitSrlNbrsDisableBtn")),
	DCX3200M_506_501_INPUT(XPATH,getPropValue("DCX3200M(506/501)Input")),
	DCX3200_HDGUIDE_518_517_INPUT(XPATH,getPropValue("DCX3200HDGuide(518/517/516/511)")),
	DCX3200P2MTC_INPUT(XPATH,getPropValue("DCX3200P2MTC(520/519)")),
	DCX3400_250GB_INPUT(XPATH,getPropValue("DCX3400(250GB)(507/502)")),
	DCX3400_500GB_INPUT(XPATH,getPropValue("DCX3400(500GB)(505/500)")),
	MODIFY_BILLING_STATUS(XPATH,getPropValue("modifyBillingStatus")),
	MODIFY_CUST_STATUS(XPATH,getPropValue("modifyCustStatus")),
	MODIFY_TV_CFS_STATUS(XPATH,getPropValue("modifyTVCfsStatus")),
	MODIFY_TV_PRODUCT_STATUS(XPATH,getPropValue("modifyTVProductStatus")),
	MODIFY_VOD_PARMS_STATUS(XPATH,getPropValue("modifyVODParmsStatus")),
	DF_REQUEST_LINK(XPATH,getPropValue("dfReuestLink")),
	WF_INFO(XPATH,getPropValue("wfInfo")),
	STANDARD_DURATION_TIME(XPATH,getPropValue("standardDurationTime")),
	LOGOUT(XPATH,getPropValue("logout")),
	VIEW_360_TAB(XPATH,getPropValue("360ViewTab")),
	HARDWARE_RETURN_ORDER(XPATH,getPropValue("HardwareReturnOrder")),
	WIRING_STATUS(XPATH,getPropValue("wiringStatus")),
	PON(XPATH,getPropValue("PON")),
	TASK_VERSION(XPATH,getPropValue("taskVersion")),
	CN_TYPE(XPATH,getPropValue("cnType")),
	DUE_DATE(XPATH,getPropValue("dueDate")),
	TN(XPATH,getPropValue("TN")),
	ONSP(XPATH,getPropValue("onsp")),
	ACCOUNT_ID(XPATH,getPropValue("accountID")),
	WORKORDER_NUMBER(XPATH,getPropValue("workOrderNumber")),
	DUAL_JACK_REPORT(XPATH,getPropValue("dualJackReport")),
	SUSPENSION_HISTORY(LINK_TEXT,getPropValue("suspensionHistory")),
	COM_INSTANCES(XPATH,getPropValue("comInstances")),
	SOM_INSTANCES(XPATH,getPropValue("somInstances")),
	ORDER_CLOSE(XPATH,getPropValue("closeOrder")),
	ROWS_IN_COM_ORDER(XPATH,getPropValue("rowsInComOrder")),
	ROWS_IN_CLEC_ORDER(XPATH,getPropValue("rowsInClecOrder")),
	CUSTOMER_ORDER_TAB(XPATH,getPropValue("customerorderTab")),
	HARDWARE_TAB(XPATH,getPropValue("hardwareTab")),
	HARDWARE_TABLES(XPATH,getPropValue("hardwareTables")),
	HARDWARE_BODY(XPATH,getPropValue("hardwareBody")),
	CPE_EXPECTED_STATUS_TEXTS(XPATH,getPropValue("CPEexpectedStatusTexts")),
	CPE_EXPECTED_STATUS(XPATH,getPropValue("CPEexpectedstatus")),
	CPE_EXPECTED_STATUS_TEXT1(XPATH,getPropValue("CPEexpectedstatustext1")),
	CPE_EXPECTED_STATUS_TEXT2(XPATH,getPropValue("CPEexpectedstatustext2")),
	CPE_EXPECTED_STATUS_TEXT3(XPATH,getPropValue("CPEexpectedstatustext3")),
	CPE_EXPECTED_STATUS_TEXT4(XPATH,getPropValue("CPEexpectedstatustext4")),
	CPE_ACTUAL_STATUS(XPATH,getPropValue("CPEactualstatus")),
	CPE_ACTUAL_STATUS_TEXT1(XPATH,getPropValue("CPEactualstatustext1")),
	CPE_ACTUAL_STATUS_TEXT2(XPATH,getPropValue("CPEactualstatustext2")),
	CPE_ACTUAL_STATUS_TEXT3(XPATH,getPropValue("CPEactualstatustext3")),
	CPE_ACTUAL_STATUS_TEXT4(XPATH,getPropValue("CPEactualstatustext4")),
	HARDWARE_TABLE(XPATH,getPropValue("hardwareTable")),
	IS_CPE_STATUS_CONSISTENT_COL(XPATH,getPropValue("IsCPEstatusconsistentCol")),
	IS_CPE_STATUS_CONSISTENT_TEXT1(XPATH,getPropValue("IsCPEstatusconsistentText1")),
	IS_CPE_STATUS_CONSISTENT_TEXT2(XPATH,getPropValue("IsCPEstatusconsistentText2")),
	IS_CPE_STATUS_CONSISTENT_TEXT3(XPATH,getPropValue("IsCPEstatusconsistentText3")),
	IS_CPE_STATUS_CONSISTENT_TEXT4(XPATH,getPropValue("IsCPEstatusconsistentText4")),
	HPE_PM_EXPECTED_STATUS_COL(XPATH,getPropValue("HPEpmexpectedstatus")),
	HPE_PM_EXPECTED_STATUS_TEXT1(XPATH,getPropValue("HPEpmexpectedstatusText1")),
	HPE_PM_EXPECTED_STATUS_TEXT2(XPATH,getPropValue("HPEpmexpectedstatusText2")),
	HPE_PM_EXPECTED_STATUS_TEXT3(XPATH,getPropValue("HPEpmexpectedstatusText3")),
	HPE_PM_EXPECTED_STATUS_TEXT4(XPATH,getPropValue("HPEpmexpectedstatusText4")),
	HPE_PM_ACTUAL_STATUS_COL(XPATH,getPropValue("HPEpmactualstatus")),
	HPE_PM_ACTUAL_STATUS_TEXT1(XPATH,getPropValue("HPEpmactualstatusText1")),
	HPE_PM_ACTUAL_STATUS_TEXT2(XPATH,getPropValue("HPEpmactualstatusText2")),
	HPE_PM_ACTUAL_STATUS_TEXT3(XPATH,getPropValue("HPEpmactualstatusText3")),
	HPE_PM_ACTUAL_STATUS_TEXT4(XPATH,getPropValue("HPEpmactualstatusText4")),
	IS_HPE_PM_STATUS_CONSISTENT_COL(XPATH,getPropValue("IsHPEpmstatusconsistentCol")),
	IS_HPE_PM_STATUS_CONSISTENT_TEXT1(XPATH,getPropValue("IsHPEpmstatusconsistentText1")),
	IS_HPE_PM_STATUS_CONSISTENT_TEXT2(XPATH,getPropValue("IsHPEpmstatusconsistentText2")),
	IS_HPE_PM_STATUS_CONSISTENT_TEXT3(XPATH,getPropValue("IsHPEpmstatusconsistentText3")),
	IS_HPE_PM_STATUS_CONSISTENT_TEXT4(XPATH,getPropValue("IsHPEpmstatusconsistentText4")),
	EXPECTED_STATUS_IN_RPIL_COL(XPATH,getPropValue("expectedstatusinRPILcol")),
	EXPECTED_STATUS_IN_RPIL_TEXT1(XPATH,getPropValue("expectedstatusinRPILText1")),
	EXPECTED_STATUS_IN_RPIL_TEXT2(XPATH,getPropValue("expectedstatusinRPILText2")),
	EXPECTED_STATUS_IN_RPIL_TEXT3(XPATH,getPropValue("expectedstatusinRPILText3")),
	EXPECTED_STATUS_IN_RPIL_TEXT4(XPATH,getPropValue("expectedstatusinRPILText4")),
	ACTUAL_STATUS_IN_RPIL_COL(XPATH,getPropValue("actualStatusinRPILcol")),
	ACTUAL_STATUS_IN_RPIL_TEXT1(XPATH,getPropValue("actualStatusinRPILText1")),
	ACTUAL_STATUS_IN_RPIL_TEXT2(XPATH,getPropValue("actualStatusinRPILText2")),
	ACTUAL_STATUS_IN_RPIL_TEXT3(XPATH,getPropValue("actualStatusinRPILText3")),
	ACTUAL_STATUS_IN_RPIL_TEXT4(XPATH,getPropValue("actualStatusinRPILText4")),
	IS_RPIL_STATUS_CONSISTENT_COL(XPATH,getPropValue("IsRPILstatusconsistentCol")),
	IS_RPIL_STATUS_CONSISTENT_TEXT1(XPATH,getPropValue("IsRPILstatusconsistentText1")),
	IS_RPIL_STATUS_CONSISTENT_TEXT2(XPATH,getPropValue("IsRPILstatusconsistentText2")),
	IS_RPIL_STATUS_CONSISTENT_TEXT3(XPATH,getPropValue("IsRPILstatusconsistentText3")),
	IS_RPIL_STATUS_CONSISTENT_TEXT4(XPATH,getPropValue("IsRPILstatusconsistentText4")),
	BRM_EXPECTED_STATUS_COL(XPATH,getPropValue("BRMexpectedstatusCol")),
	BRM_EXPECTED_STATUS_TEXT1(XPATH,getPropValue("BRMexpectedstatusText1")),
	BRM_EXPECTED_STATUS_TEXT2(XPATH,getPropValue("BRMexpectedstatusText2")),
	BRM_EXPECTED_STATUS_TEXT3(XPATH,getPropValue("BRMexpectedstatusText3")),
	BRM_EXPECTED_STATUS_TEXT4(XPATH,getPropValue("BRMexpectedstatusText4")),
	BRM_ACTUAL_STATUS_COL(XPATH,getPropValue("BRMactualstatusCol")),
	BRM_ACTUAL_STATUS_TEXT1(XPATH,getPropValue("BRMactualstatusText1")),
	BRM_ACTUAL_STATUS_TEXT2(XPATH,getPropValue("BRMactualstatusText2")),
	BRM_ACTUAL_STATUS_TEXT3(XPATH,getPropValue("BRMactualstatusText3")),
	BRM_ACTUAL_STATUS_TEXT4(XPATH,getPropValue("BRMactualstatusText4")),
	IS_BRM_STATUS_CONSISTENT_COL(XPATH,getPropValue("IsBRMstatusconsistentCol")),
	IS_BRM_STATUS_CONSISTENT_TEXT1(XPATH,getPropValue("IsBRMstatusconsistentText1")),
	IS_BRM_STATUS_CONSISTENT_TEXT2(XPATH,getPropValue("IsBRMstatusconsistentText2")),
	IS_BRM_STATUS_CONSISTENT_TEXT3(XPATH,getPropValue("IsBRMstatusconsistentText3")),
	IS_BRM_STATUS_CONSISTENT_TEXT4(XPATH,getPropValue("IsBRMstatusconsistentText4")),
	PHONE_NUMBERS_TABLE(XPATH,getPropValue("phonenumbersTab")),
	PHONE_HPE_PM_EXPECTED_STATUS_COL(XPATH,getPropValue("phoneHPEpmexpectedstatus")),
	PHONE_HPE_PM_EXPECTED_STATUS_TEXT1(XPATH,getPropValue("phoneHPEpmexpectedstatusText1")),
	PHONE_HPE_PM_EXPECTED_STATUS_TEXT2(XPATH,getPropValue("phoneHPEpmexpectedstatusText2")),
	PHONE_HPE_PM_ACTUAL_STATUS_COL(XPATH,getPropValue("phoneHPEpmactualstatus")),
	PHONE_HPE_PM_ACTUAL_STATUS_TEXT1(XPATH,getPropValue("phoneHPEpmactualstatusText1")),
	PHONE_HPE_PM_ACTUAL_STATUS_TEXT2(XPATH,getPropValue("phoneHPEpmactualstatusText2")),
	PHONE_IS_HPE_PM_STATUS_COL(XPATH,getPropValue("phoneIsHPEpmstatusconsistentCol")),
	PHONE_IS_HPE_PM_STATUS_TEXT1(XPATH,getPropValue("phoneIsHPEpmstatusconsistentText1")),
	PHONE_IS_HPE_PM_STATUS_TEXT2(XPATH,getPropValue("phoneIsHPEpmstatusconsistentText2")),
	PHONE_BRM_EXPECTED_STATUS_COL(XPATH,getPropValue("phoneBRMexpectedstatusCol")),
	PHONE_BRM_EXPECTED_STATUS_TEXT1(XPATH,getPropValue("phoneBRMexpectedstatusText1")),
	PHONE_BRM_EXPECTED_STATUS_TEXT2(XPATH,getPropValue("phoneBRMexpectedstatusText2")),
	PHONE_BRM_ACTUAL_STATUS_COL(XPATH,getPropValue("phoneBRMactualstatusCol")),
	PHONE_BRM_ACTUAL_STATUS_TEXT1(XPATH,getPropValue("phoneBRMactualstatusText1")),
	PHONE_BRM_ACTUAL_STATUS_TEXT2(XPATH,getPropValue("phoneBRMactualstatusText2")),
	PHONE_IS_BRM_STATUS_CONSISTENT_COL(XPATH,getPropValue("phoneIsBRMstatusconsistentCol")),
	PHONE_IS_BRM_STATUS_CONSISTENT_TEXT1(XPATH,getPropValue("phoneIsBRMstatusconsistentText1")),
	PHONE_IS_BRM_STATUS_CONSISTENT_TEXT2(XPATH,getPropValue("phoneIsBRMstatusconsistentText2")),
	PHONE_TNI_EXPECTED_STATUS_COL(XPATH,getPropValue("phoneTNIexpectedstatusCol")),
	PHONE_TNI_EXPECTED_STATUS_TEXT1(XPATH,getPropValue("phoneTNIexpectedstatusText1")),
	PHONE_TNI_EXPECTED_STATUS_TEXT2(XPATH,getPropValue("phoneTNIexpectedstatusText2")),
	PHONE_TNI_ACTUAL_STATUS_COL(XPATH,getPropValue("phoneTNIactualstatusCol")),
	PHONE_TNI_ACTUAL_STATUS_TEXT1(XPATH,getPropValue("phoneTNIactualstatusText1")),
	PHONE_TNI_ACTUAL_STATUS_TEXT2(XPATH,getPropValue("phoneTNIactualstatusText2")),
	EMAILS_TABLE(XPATH,getPropValue("EmailsTab")),
	EMAIL_HPE_PM_EXPECTED_STATUS_COL(XPATH,getPropValue("EmailHPEpmExpectedStatusCol")),
	EMAIL_HPE_PM_EXPECTED_STATUS_TEXT(XPATH,getPropValue("EmailHPEpmExpectedStatusText")),
	EMAIL_HPE_PM_ACTUAL_STATUS_COL(XPATH,getPropValue("EmailHPEpmActualStatusCol")),
	EMAIL_HPE_PM_ACTUAL_STATUS_TEXT(XPATH,getPropValue("EmailHPEpmActualStatusText")),
	EMAIL_IS_HPE_PM_STATUS_CONSISTENT_COL(XPATH,getPropValue("EmailIsHPEpmStatusConsistentCol")),
	EMAIL_IS_HPE_PM_STATUS_CONSISTENT_TEXT(XPATH,getPropValue("EmailIsHPEpmStatusConsistentText")),
	NPAC_ACTIVATE_COMPLETED(XPATH,getPropValue("npacActivateCompleted")),
	
	
	
//	CUSTOMER_PORTAL_TAB(XPATH,getPropValue("CustomerPortalTab")),
	FIBRE75_TAB(XPATH,getPropValue("Fibre75")),
	ACTIVE_DOCUMENT_TAB(XPATH,getPropValue("ActiveDocument")),
	DID_NOT_REQUEST_TEXT(XPATH,getPropValue("DidNotRequest"));
	
	
	private Locator locator;
	private String expression;
	
	public static String getPropValue(String value){
        value = Utility.getValueFromPropertyFile( value, Constants.COM_ORDER_PAGE_PROP_PATH);
        return value;
    }
	
	COMOrdersPageElement(Locator locator, String expression){
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
