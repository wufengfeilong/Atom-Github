package com.fujisoft.update;
/**
 * 定義
 * @author 860115025
 * 2017/09/18
 */
public class Constant {
    // ドメイン:「172.29.140.35」の証書
    public static final String cerInNetStr = "-----BEGIN CERTIFICATE-----\n" +
            "MIIDeTCCAmGgAwIBAgIETpiuEjANBgkqhkiG9w0BAQsFADBtMQswCQYDVQQGEwJj\n" +
            "bjERMA8GA1UECBMIc2hhbmRvbmcxDjAMBgNVBAcTBWppbmFuMRAwDgYDVQQKEwdj\n" +
            "b21wYW55MREwDwYDVQQLEwhmdWppc29mdDEWMBQGA1UEAxMNMTcyLjI5LjE0MC4z\n" +
            "NTAeFw0xNzA4MTAwMzU3NThaFw0yNzA2MTkwMzU3NThaMG0xCzAJBgNVBAYTAmNu\n" +
            "MREwDwYDVQQIEwhzaGFuZG9uZzEOMAwGA1UEBxMFamluYW4xEDAOBgNVBAoTB2Nv\n" +
            "bXBhbnkxETAPBgNVBAsTCGZ1amlzb2Z0MRYwFAYDVQQDEw0xNzIuMjkuMTQwLjM1\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8N4+gqj6LHyD8nNmc6lf\n" +
            "5XNGtcCzVAaT5EwMSFw2tm6pvAE6f4PlczMlLZgpuADi4kseH4EH5Bf43RGUliP1\n" +
            "BPT9yvHa+y9tB2dTYmmQ8HV4dDN1gribYEGP9UKQGt9v4HJDROrXX0Mep3qSBSkC\n" +
            "oSZwnOm8dK8Cy7HYxr5hsrgeQA6vBap4WPZOQkI2eKhj8MqWE24hPLViwhZy2vIz\n" +
            "FH/dOw4YxjQNYYuIyn/awGo/AzxOFeqcKGI7X4ZYuGF44gvz++9H3lvvxnBgtbPt\n" +
            "tyCKNL/Vbk9Nr9hDGSCHjewjb/cPkYg+VxXx8PvPQUOttbdxjJ4Y5t+sS2qThA8G\n" +
            "+QIDAQABoyEwHzAdBgNVHQ4EFgQUxCTaDc8wY6bq+ffgaO2O4cMoY6kwDQYJKoZI\n" +
            "hvcNAQELBQADggEBAKxbLBWhnoFTaDb1vCqTyV4LbNIfDEK9NM529QusJZo9atgV\n" +
            "sjhUM39XeMeYCZDJF8jFcZAiJDBweQIXgqsDs6H+8NHIQifOjGMYzJqJBubV7djJ\n" +
            "kunFACSpIreYGAdRr/zoEa+1rEFANvFS9jyFCXvdgO/YZe2c29nXiJCDduZHrvTM\n" +
            "WyjTM2fY2hK2Q5b3l3nInMn6CFjt+l4OWVvB5kclX2/HP31mMAAcWtM2oY95MkbN\n" +
            "nQkDi+JiZivnCerU0YwrzCrQ9Plxa3cF5yT3ffhLj3xbqtq7SGBS8V0QoYM6Vto4\n" +
            "zEsW7UXuzqfbDU7FGmkBDlhxcunKFi/Sr+2cGRY=\n" +
            "-----END CERTIFICATE-----";
    // ドメイン：「172.29.140.45」の証書
    public static final String cerOutNetStr = "-----BEGIN CERTIFICATE-----\n" +
            "MIIDgzCCAmugAwIBAgIER6HKKjANBgkqhkiG9w0BAQsFADByMRAwDgYDVQQGEwdV\n" +
            "bmtub3duMRAwDgYDVQQIEwdVbmtub3duMRAwDgYDVQQHEwdVbmtub3duMRAwDgYD\n" +
            "VQQKEwdVbmtub3duMRAwDgYDVQQLEwdVbmtub3duMRYwFAYDVQQDEw0xNzIuMjku\n" +
            "MTQwLjQ1MB4XDTE3MDkyOTA2MjcwNloXDTI3MDgwODA2MjcwNlowcjEQMA4GA1UE\n" +
            "BhMHVW5rbm93bjEQMA4GA1UECBMHVW5rbm93bjEQMA4GA1UEBxMHVW5rbm93bjEQ\n" +
            "MA4GA1UEChMHVW5rbm93bjEQMA4GA1UECxMHVW5rbm93bjEWMBQGA1UEAxMNMTcy\n" +
            "LjI5LjE0MC40NTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJs4Kkrl\n" +
            "AJrJ6H3acPa6nef4pmWYMawS0IGnunImpSgG5kqLWKlqzIqoy8K3GUuHMkHTQGKB\n" +
            "+D4Qk+RoOMeSbegG7ogP9IMDy//DQEIykD0nf6ntrpwS+djn3KZzlP7wAwh9Symq\n" +
            "ThQIsA0x0g9CM4VPjs1TSoy6tuxlZn+P2ACrRTEt9cW+RhZlqEl8hT2l9BzE6d3M\n" +
            "PtWuQGNUp5HNRHgej7zvoQNAn/JjhrZl9PHp3ABvlfLzr6FgdvcGdFTQwOqU6Y1/\n" +
            "ShUtmD/UuswKEn/JjcaTu+ogwqc/KWC+/hzpPO3UtAEyL/+hmhzjkAnz9pDFL6o/\n" +
            "h8XG8ujGFIkIbucCAwEAAaMhMB8wHQYDVR0OBBYEFEMXYI7+0UTNnrz3m5fibN43\n" +
            "AjwgMA0GCSqGSIb3DQEBCwUAA4IBAQAeygM1a5WXYo82uiMFBJKeT+w9sN5LTWK+\n" +
            "4UtxIt8B+p0GUrBL9H0uUiZyG0YGXP70f/eQjImS9ryBroyGNKYAAzk0ObiAvKEi\n" +
            "YHGMq3igzeVWZFDt7QWQ/suKQW3RXmgs5pLWPQfJAMnk/JyFwtkBhplDy92W2sAK\n" +
            "RGpTPxy4Z53lhskEyLVB7nrmEUYbl7PmYezl8EAhw9+kc81Eco8i3ftHDGzPOYFd\n" +
            "+OJhjxUs7yVrCv42AVqH2VgqNpoDCzmkLYyxdeHoUoLA93oIzh13fz2EQq23A2g7\n" +
            "HXA5ukinwGOvshiESEXEzhKptZBD8gpvhHeQhou2hW+IQFwHhM6s\n" +
            "-----END CERTIFICATE-----";
    // 証書パスワード
    public static final String CER_PWD = "123456";

    // 動画リクエストコード
    public static final int VIDEO_REQUEST_CODE = 0x01;
    // 権限リクエストコード
    public static final int PERMISSION_REQUEST_CODE = 0x02;
}
