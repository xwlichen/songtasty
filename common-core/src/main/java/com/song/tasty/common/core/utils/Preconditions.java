package com.song.tasty.common.core.utils;

import android.text.TextUtils;

import java.util.Collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @date : 2019-07-22 11:49
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class Preconditions {


    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 检验参数合法性
     *
     * @param expression   是否合法
     * @param errorMessage 输出信息
     */
    public static void checkArgument(boolean expression, @Nullable Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }


    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        } else {
            return reference;
        }
    }

    public static <T> T checkNotNull(T reference, @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs) {
        if (reference == null) {
            throw new NullPointerException(format(errorMessageTemplate, errorMessageArgs));
        } else {
            return reference;
        }
    }


    public static String checkNotEmpty(String string) {
        if (TextUtils.isEmpty(string)) {
            throw new IllegalArgumentException("value not be null or empty");
        }
        return string;
    }

    @NonNull
    public static <T extends Collection<Y>, Y> T checkNotEmpty(@NonNull T collection) {
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("value not be empty.");
        }
        return collection;
    }


    static String format(String template, @Nullable Object... args) {
        template = String.valueOf(template);
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;

        int i;
        int placeholderStart;
        for (i = 0; i < args.length; templateStart = placeholderStart + 2) {
            placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }

            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
        }

        builder.append(template.substring(templateStart));
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);

            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }

            builder.append(']');
        }

        return builder.toString();
    }
}
