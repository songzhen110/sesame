package com.coder.common.util.validation;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Objects;

public class CollectionUtils {

    /**
     *
     * @param newObjects
     * @param oldObjects
     * @param del 左边
     * @param add 右边
     * @param update 交集
     */
    public static void buildEditList(@NotNull Collection<Object> newObjects, @NotNull Collection<Object> oldObjects, @NotNull Collection<Object> del,
                                     @NotNull Collection<Object> add, @NotNull Collection<Object> update) {
        newObjects.forEach(e -> {

             Object slave = oldObjects.contains(e)?e:null;

            if (Objects.nonNull(slave)) {
                // 保留的元素
                update.add(slave);
                oldObjects.remove(slave);
            } else {
                // 新增的元素
                add.add(e);
            }
        });
        // 移除的元素
        del.addAll(oldObjects);
    }
}
