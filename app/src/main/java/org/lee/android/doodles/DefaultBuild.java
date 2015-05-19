package org.lee.android.doodles;

import org.lee.android.doodles.fragment.YearsFragment;

/**
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-4-13.
 */
public class DefaultBuild {


    public static String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgwvDBfTguJUMxBHEBoBOo5o+yT/gjSs9GAnr7HVpzqPz1yMeodRRuwro+GVWSjLqXjmHhgp+KH+8eS+yiXfxa5mXzmAZlJCMyyLYMjKgvIV4I+I5SAqVO5wriPAfcF1KbXUMufWDrvnVb6aThqlQJE+14yb8cxBPWezZ0zrrXsjaC+R+u/JY2DrW38zKW6nl2UA1tpsNy/4yMz5hXEgUinWSD3fiu/qIQRHetzAY2y0jxaJjk3A/C/k7pini4g4gKelasiLHyGYWGhqBBAkql2uTYCfaDxF5GkJdV+masR7qzpJt/UgGYnWholHG3h9UO3xQ5W3qpguxwz9QBMBoawIDAQAB";

    /**
     * 默认的年份
     * @return
     */
    public static YearsFragment.Year defaultYear(){
        return new YearsFragment.Year(2015, "i_2002");
    }
}
