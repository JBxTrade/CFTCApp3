import { element, by, ElementFinder } from 'protractor';

export class I18nComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-i-18-n div table .btn-danger'));
  title = element.all(by.css('jhi-i-18-n div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class I18nUpdatePage {
  pageTitle = element(by.id('jhi-i-18-n-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  frInput = element(by.id('field_fr'));
  enInput = element(by.id('field_en'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setFrInput(fr: string): Promise<void> {
    await this.frInput.sendKeys(fr);
  }

  async getFrInput(): Promise<string> {
    return await this.frInput.getAttribute('value');
  }

  async setEnInput(en: string): Promise<void> {
    await this.enInput.sendKeys(en);
  }

  async getEnInput(): Promise<string> {
    return await this.enInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class I18nDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-i18n-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-i18n'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
