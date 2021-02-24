package com.chase.sep.columbus.mentoring;

import java.util.ArrayList;
import java.util.List;

public class PairingUtil {

    public static <S extends Pairable<T>, T extends Pairable<S>> List<Pair<S, T>> pair(
            List<S> list1,
            List<T> list2
    ) {
        // build full preferences lists
        for (S element : list1) {
            element.setPreferences(list2);
        }

        for (T element : list2) {
            element.setPreferences(list1);
        }

        while (freeElement(list1) && freeElement(list2)) {
            S freeList1Element = list1.stream().filter(Pairable::isFree).findFirst().orElse(null);
            if (freeList1Element != null) {
                // get top preference
                T topPreference = freeList1Element.topChoice();
                if (topPreference != null) {
                    if (topPreference.isFree()) {
                        freeList1Element.replaceMatch(topPreference);
                        topPreference.replaceMatch(freeList1Element);
                    } else {
                        if (topPreference.prefer(freeList1Element)) {
                            S topPreferenceOldMatch = topPreference.replaceMatch(freeList1Element);
                            if (topPreferenceOldMatch != null) {
                                topPreferenceOldMatch.separate(topPreference);
                            }

                            freeList1Element.replaceMatch(topPreference);
                        }
                    }
                }
            }
        }

        List<Pair<S, T>> pairs = new ArrayList<>();
        for (S element : list1) {
            pairs.add(new Pair<>(element, element.matches()));
        }

        return pairs;
    }

    private static <T extends Pairable<?>> boolean freeElement(List<T> list) {
        return list.stream().anyMatch(T::isFree);
    }

}
