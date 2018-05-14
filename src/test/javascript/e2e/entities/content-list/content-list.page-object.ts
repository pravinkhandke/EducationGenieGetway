import { element, by } from 'protractor';

export class ContentListComponentsPage {
    createButton = element(by.css('#jh-create-entity'));
    title = element.all(by.css('jhi-content-list div h2#page-heading span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ContentListUpdatePage {
    PageTitle = element(by.css('h2#jhi-content-list-heading'));
    saveButton = element(by.css('#save-entity'));
    cancelButton = element(by.css('#cancel-save'));
    listTitleInput = element(by.css('input#field_listTitle'));
    contentSelect = element(by.css('select#field_content'));

    getPageTitle() {
        return this.PageTitle.getAttribute('jhiTranslate');
    }

    setListTitleInput(listTitle) {
        this.listTitleInput.sendKeys(listTitle);
    }

    getListTitleInput() {
        return this.listTitleInput.getAttribute('value');
    }

    contentSelectLastOption() {
        this.contentSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    contentSelectOption(option) {
        this.contentSelect.sendKeys(option);
    }

    getContentSelect() {
        return this.contentSelect;
    }

    getContentSelectedOption() {
        return this.contentSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    cancel() {
        this.cancelButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
