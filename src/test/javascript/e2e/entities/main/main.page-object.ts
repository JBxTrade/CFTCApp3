import { element, by, ElementFinder } from 'protractor';

export class MainComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-main div table .btn-danger'));
  title = element.all(by.css('jhi-main div h2#page-heading span')).first();
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

export class MainUpdatePage {
  pageTitle = element(by.id('jhi-main-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  theRoleSelect = element(by.id('field_theRole'));
  imageInput = element(by.id('file_image'));

  subLinkSelect = element(by.id('field_subLink'));
  linkSelect = element(by.id('field_link'));
  linkDataSelect = element(by.id('field_linkData'));
  titleFrSelect = element(by.id('field_titleFr'));
  titleEnSelect = element(by.id('field_titleEn'));
  descriptionFrSelect = element(by.id('field_descriptionFr'));
  descriptionEnSelect = element(by.id('field_descriptionEn'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setTheRoleSelect(theRole: string): Promise<void> {
    await this.theRoleSelect.sendKeys(theRole);
  }

  async getTheRoleSelect(): Promise<string> {
    return await this.theRoleSelect.element(by.css('option:checked')).getText();
  }

  async theRoleSelectLastOption(): Promise<void> {
    await this.theRoleSelect.all(by.tagName('option')).last().click();
  }

  async setImageInput(image: string): Promise<void> {
    await this.imageInput.sendKeys(image);
  }

  async getImageInput(): Promise<string> {
    return await this.imageInput.getAttribute('value');
  }

  async subLinkSelectLastOption(): Promise<void> {
    await this.subLinkSelect.all(by.tagName('option')).last().click();
  }

  async subLinkSelectOption(option: string): Promise<void> {
    await this.subLinkSelect.sendKeys(option);
  }

  getSubLinkSelect(): ElementFinder {
    return this.subLinkSelect;
  }

  async getSubLinkSelectedOption(): Promise<string> {
    return await this.subLinkSelect.element(by.css('option:checked')).getText();
  }

  async linkSelectLastOption(): Promise<void> {
    await this.linkSelect.all(by.tagName('option')).last().click();
  }

  async linkSelectOption(option: string): Promise<void> {
    await this.linkSelect.sendKeys(option);
  }

  getLinkSelect(): ElementFinder {
    return this.linkSelect;
  }

  async getLinkSelectedOption(): Promise<string> {
    return await this.linkSelect.element(by.css('option:checked')).getText();
  }

  async linkDataSelectLastOption(): Promise<void> {
    await this.linkDataSelect.all(by.tagName('option')).last().click();
  }

  async linkDataSelectOption(option: string): Promise<void> {
    await this.linkDataSelect.sendKeys(option);
  }

  getLinkDataSelect(): ElementFinder {
    return this.linkDataSelect;
  }

  async getLinkDataSelectedOption(): Promise<string> {
    return await this.linkDataSelect.element(by.css('option:checked')).getText();
  }

  async titleFrSelectLastOption(): Promise<void> {
    await this.titleFrSelect.all(by.tagName('option')).last().click();
  }

  async titleFrSelectOption(option: string): Promise<void> {
    await this.titleFrSelect.sendKeys(option);
  }

  getTitleFrSelect(): ElementFinder {
    return this.titleFrSelect;
  }

  async getTitleFrSelectedOption(): Promise<string> {
    return await this.titleFrSelect.element(by.css('option:checked')).getText();
  }

  async titleEnSelectLastOption(): Promise<void> {
    await this.titleEnSelect.all(by.tagName('option')).last().click();
  }

  async titleEnSelectOption(option: string): Promise<void> {
    await this.titleEnSelect.sendKeys(option);
  }

  getTitleEnSelect(): ElementFinder {
    return this.titleEnSelect;
  }

  async getTitleEnSelectedOption(): Promise<string> {
    return await this.titleEnSelect.element(by.css('option:checked')).getText();
  }

  async descriptionFrSelectLastOption(): Promise<void> {
    await this.descriptionFrSelect.all(by.tagName('option')).last().click();
  }

  async descriptionFrSelectOption(option: string): Promise<void> {
    await this.descriptionFrSelect.sendKeys(option);
  }

  getDescriptionFrSelect(): ElementFinder {
    return this.descriptionFrSelect;
  }

  async getDescriptionFrSelectedOption(): Promise<string> {
    return await this.descriptionFrSelect.element(by.css('option:checked')).getText();
  }

  async descriptionEnSelectLastOption(): Promise<void> {
    await this.descriptionEnSelect.all(by.tagName('option')).last().click();
  }

  async descriptionEnSelectOption(option: string): Promise<void> {
    await this.descriptionEnSelect.sendKeys(option);
  }

  getDescriptionEnSelect(): ElementFinder {
    return this.descriptionEnSelect;
  }

  async getDescriptionEnSelectedOption(): Promise<string> {
    return await this.descriptionEnSelect.element(by.css('option:checked')).getText();
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

export class MainDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-main-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-main'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
