package gov.michigan.dit.timeexpense.model.display;

/**
 * Extends Crystal Reports ReportClientDocument and implements the
 * Serializable interface in order to save document instances
 * in session.
 */

import java.io.Serializable;

import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;

public class TEReprotClientDocument extends ReportClientDocument implements
		Serializable {

}
