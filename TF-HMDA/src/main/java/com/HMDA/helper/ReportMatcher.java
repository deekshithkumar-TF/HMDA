package com.HMDA.helper;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;

public class ReportMatcher {

    private static Map<String, Boolean> areEqualKeyValues(Map<String, String> first, Map<String, String> second) {
        return first.entrySet().stream()
                .collect( Collectors.toMap( e -> e.getKey(),
                        e -> e.getValue().equals( second.get( e.getKey() ) ) ) );
    }

    public static void matchHeader(String reportUrl, String orderId) throws IOException {

        PdfUtils.parsePdfContent( reportUrl );
        ExcelUtils.storeDBRecordsToExcel( orderId );

        Map<String, Boolean> result = areEqualKeyValues(PdfUtils.orderDetails, ExcelUtils.order);

        assertThat(result, hasEntry( "OrderId", true ));
        assertThat(result, hasEntry("CustomerReferenceNumber", true));
        assertThat(result, hasEntry("LoanType", true));
    }

    public static void matchApplicantSection(String reportUrl, String orderId) {

        Map<String, Boolean> addressMatchResult = areEqualKeyValues(PdfUtils.orderSubjectProperty, ExcelUtils.subProp);
        Map<String, Boolean> borrowerMatchResult = areEqualKeyValues(PdfUtils.orderBorrower, ExcelUtils.borrower);

        assertThat(addressMatchResult, hasEntry( "AddressLine1", true ));
        assertThat(addressMatchResult, hasEntry("City", true));
        assertThat(addressMatchResult, hasEntry("Zip5Zip4", true));

        assertThat(borrowerMatchResult, hasEntry( "FirstName", true ));
        assertThat(borrowerMatchResult, hasEntry("LastName", true));
        assertThat(borrowerMatchResult, hasEntry("SSN", true));
//        assertThat(borrowerMatchResult, hasEntry("MaritalStatus", true));
        assertThat(borrowerMatchResult, hasEntry("NumberOfDependents", true));
    }
}
