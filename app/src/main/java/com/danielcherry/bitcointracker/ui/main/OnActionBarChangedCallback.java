package com.danielcherry.bitcointracker.ui.main;

import androidx.appcompat.widget.Toolbar;

/**
 * Callback interface called from fragments providing their parent activity with a new Toolbar.
 * It's the activity's responsibility to accept the toolbar and set it as it's own.
 */
public interface OnActionBarChangedCallback {
    void onActionBarChanged(Toolbar toolbar);
}
