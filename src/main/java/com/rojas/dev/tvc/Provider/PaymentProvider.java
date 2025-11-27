package com.rojas.dev.tvc.Provider;

public interface PaymentProvider {

    /**
     * Create a payment link at the PSP and returns the PSP link id + checkout url.
     *
     * @param amountCents amount in cents (PSP expects cents)
     * @param currency "COP"
     * @param reference internal reference
     * @param redirectUrl deep link or return url
     * @param destinationAccountId optional: merchant PSP account id (if PSP supports directing the link)
     */
    PaymentLinkResult createPaymentLink(long amountCents, String currency, String reference, String redirectUrl, String destinationAccountId);

    /**
     * Optionally get status (if PSP supports polling). Many PSPs rely on webhooks; implement as needed.
     */
    PaymentLinkStatusResult getPaymentLinkStatus(String paymentLinkId);
}
