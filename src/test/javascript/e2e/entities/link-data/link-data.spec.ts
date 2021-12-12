import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LinkDataComponentsPage, LinkDataDeleteDialog, LinkDataUpdatePage } from './link-data.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('LinkData e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let linkDataComponentsPage: LinkDataComponentsPage;
  let linkDataUpdatePage: LinkDataUpdatePage;
  let linkDataDeleteDialog: LinkDataDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LinkData', async () => {
    await navBarPage.goToEntity('link-data');
    linkDataComponentsPage = new LinkDataComponentsPage();
    await browser.wait(ec.visibilityOf(linkDataComponentsPage.title), 5000);
    expect(await linkDataComponentsPage.getTitle()).to.eq('cftcApp3App.linkData.home.title');
    await browser.wait(ec.or(ec.visibilityOf(linkDataComponentsPage.entities), ec.visibilityOf(linkDataComponentsPage.noResult)), 1000);
  });

  it('should load create LinkData page', async () => {
    await linkDataComponentsPage.clickOnCreateButton();
    linkDataUpdatePage = new LinkDataUpdatePage();
    expect(await linkDataUpdatePage.getPageTitle()).to.eq('cftcApp3App.linkData.home.createOrEditLabel');
    await linkDataUpdatePage.cancel();
  });

  it('should create and save LinkData', async () => {
    const nbButtonsBeforeCreate = await linkDataComponentsPage.countDeleteButtons();

    await linkDataComponentsPage.clickOnCreateButton();

    await promise.all([
      linkDataUpdatePage.theRoleSelectLastOption(),
      linkDataUpdatePage.setImageCardInput(absolutePath),
      linkDataUpdatePage.setImageInput(absolutePath),
      linkDataUpdatePage.setImage2Input(absolutePath),
      linkDataUpdatePage.setImage3Input(absolutePath),
      linkDataUpdatePage.titleFrSelectLastOption(),
      linkDataUpdatePage.titleEnSelectLastOption(),
      linkDataUpdatePage.descriptionFrSelectLastOption(),
      linkDataUpdatePage.descriptionEnSelectLastOption(),
      linkDataUpdatePage.codeFrSelectLastOption(),
      linkDataUpdatePage.codeEnSelectLastOption(),
      linkDataUpdatePage.subSelectLastOption(),
      linkDataUpdatePage.unSubSelectLastOption(),
    ]);

    expect(await linkDataUpdatePage.getImageCardInput()).to.endsWith(
      fileNameToUpload,
      'Expected ImageCard value to be end with ' + fileNameToUpload
    );
    expect(await linkDataUpdatePage.getImageInput()).to.endsWith(
      fileNameToUpload,
      'Expected Image value to be end with ' + fileNameToUpload
    );
    expect(await linkDataUpdatePage.getImage2Input()).to.endsWith(
      fileNameToUpload,
      'Expected Image2 value to be end with ' + fileNameToUpload
    );
    expect(await linkDataUpdatePage.getImage3Input()).to.endsWith(
      fileNameToUpload,
      'Expected Image3 value to be end with ' + fileNameToUpload
    );

    await linkDataUpdatePage.save();
    expect(await linkDataUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await linkDataComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last LinkData', async () => {
    const nbButtonsBeforeDelete = await linkDataComponentsPage.countDeleteButtons();
    await linkDataComponentsPage.clickOnLastDeleteButton();

    linkDataDeleteDialog = new LinkDataDeleteDialog();
    expect(await linkDataDeleteDialog.getDialogTitle()).to.eq('cftcApp3App.linkData.delete.question');
    await linkDataDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(linkDataComponentsPage.title), 5000);

    expect(await linkDataComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
