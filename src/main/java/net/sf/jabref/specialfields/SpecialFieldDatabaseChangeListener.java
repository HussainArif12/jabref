package net.sf.jabref.specialfields;

import net.sf.jabref.model.BibtexEntry;
import net.sf.jabref.DatabaseChangeEvent;
import net.sf.jabref.DatabaseChangeListener;
import net.sf.jabref.JabRef;
import net.sf.jabref.gui.undo.NamedCompound;
import net.sf.jabref.logic.l10n.Localization;

public class SpecialFieldDatabaseChangeListener implements
        DatabaseChangeListener {

    private static SpecialFieldDatabaseChangeListener INSTANCE;


    @Override
    public void databaseChanged(DatabaseChangeEvent e) {
        if (e.getType() == DatabaseChangeEvent.ChangeType.ADDED_ENTRY &&
                SpecialFieldsUtils.keywordSyncEnabled()) {
            final BibtexEntry entry = e.getEntry();
            // NamedCompount code similar to SpecialFieldUpdateListener
            NamedCompound nc = new NamedCompound(Localization.lang("Synchronized special fields based on keywords"));
            SpecialFieldsUtils.syncSpecialFieldsFromKeywords(entry, nc);
            nc.end();
            JabRef.jrf.basePanel().undoManager.addEdit(nc);
        }
    }

    public static SpecialFieldDatabaseChangeListener getInstance() {
        if (SpecialFieldDatabaseChangeListener.INSTANCE == null) {
            SpecialFieldDatabaseChangeListener.INSTANCE = new SpecialFieldDatabaseChangeListener();
        }
        return SpecialFieldDatabaseChangeListener.INSTANCE;
    }

}
