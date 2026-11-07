/*
 * SPDX-FileCopyrightText: 2026 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.android.launcher3.allapps.search

import android.content.Context
import com.android.launcher3.LauncherPrefs
import com.android.launcher3.util.ComponentKey

/**
 * The apps last opened from the all apps search, most recent first, shown when the search field
 * is focused while still empty.
 */
object RecentSearchApps {
    private const val MAX_APPS = 10
    private const val SEPARATOR = "\n"
    private val RECENT_SEARCH_APPS = LauncherPrefs.nonRestorableItem("recent_search_apps", "")

    /** Moves [key] to the front of the recent search apps. */
    @JvmStatic
    fun add(context: Context, key: ComponentKey) {
        val keys = get(context).filter { it != key }.toMutableList()
        keys.add(0, key)
        LauncherPrefs.get(context)
            .put(RECENT_SEARCH_APPS, keys.take(MAX_APPS).joinToString(SEPARATOR))
    }

    /** Returns the recent search apps, most recent first. */
    @JvmStatic
    fun get(context: Context): List<ComponentKey> =
        LauncherPrefs.get(context)
            .get(RECENT_SEARCH_APPS)
            .split(SEPARATOR)
            .filter { it.isNotEmpty() }
            .mapNotNull { ComponentKey.fromString(it) }
}
