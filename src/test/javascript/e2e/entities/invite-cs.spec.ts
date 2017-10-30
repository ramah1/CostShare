import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Invite e2e test', () => {

    let navBarPage: NavBarPage;
    let inviteDialogPage: InviteDialogPage;
    let inviteComponentsPage: InviteComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Invites', () => {
        navBarPage.goToEntity('invite-cs');
        inviteComponentsPage = new InviteComponentsPage();
        expect(inviteComponentsPage.getTitle()).toMatch(/costshareApp.invite.home.title/);

    });

    it('should load create Invite dialog', () => {
        inviteComponentsPage.clickOnCreateButton();
        inviteDialogPage = new InviteDialogPage();
        expect(inviteDialogPage.getModalTitle()).toMatch(/costshareApp.invite.home.createOrEditLabel/);
        inviteDialogPage.close();
    });

    it('should create and save Invites', () => {
        inviteComponentsPage.clickOnCreateButton();
        inviteDialogPage.setCommentInput('comment');
        expect(inviteDialogPage.getCommentInput()).toMatch('comment');
        inviteDialogPage.getAcceptedInput().isSelected().then(function (selected) {
            if (selected) {
                inviteDialogPage.getAcceptedInput().click();
                expect(inviteDialogPage.getAcceptedInput().isSelected()).toBeFalsy();
            } else {
                inviteDialogPage.getAcceptedInput().click();
                expect(inviteDialogPage.getAcceptedInput().isSelected()).toBeTruthy();
            }
        });
        inviteDialogPage.groupSelectLastOption();
        inviteDialogPage.sentToSelectLastOption();
        inviteDialogPage.sentFromSelectLastOption();
        inviteDialogPage.save();
        expect(inviteDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class InviteComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-invite-cs div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class InviteDialogPage {
    modalTitle = element(by.css('h4#myInviteLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    commentInput = element(by.css('input#field_comment'));
    acceptedInput = element(by.css('input#field_accepted'));
    groupSelect = element(by.css('select#field_group'));
    sentToSelect = element(by.css('select#field_sentTo'));
    sentFromSelect = element(by.css('select#field_sentFrom'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setCommentInput = function (comment) {
        this.commentInput.sendKeys(comment);
    }

    getCommentInput = function () {
        return this.commentInput.getAttribute('value');
    }

    getAcceptedInput = function () {
        return this.acceptedInput;
    }
    groupSelectLastOption = function () {
        this.groupSelect.all(by.tagName('option')).last().click();
    }

    groupSelectOption = function (option) {
        this.groupSelect.sendKeys(option);
    }

    getGroupSelect = function () {
        return this.groupSelect;
    }

    getGroupSelectedOption = function () {
        return this.groupSelect.element(by.css('option:checked')).getText();
    }

    sentToSelectLastOption = function () {
        this.sentToSelect.all(by.tagName('option')).last().click();
    }

    sentToSelectOption = function (option) {
        this.sentToSelect.sendKeys(option);
    }

    getSentToSelect = function () {
        return this.sentToSelect;
    }

    getSentToSelectedOption = function () {
        return this.sentToSelect.element(by.css('option:checked')).getText();
    }

    sentFromSelectLastOption = function () {
        this.sentFromSelect.all(by.tagName('option')).last().click();
    }

    sentFromSelectOption = function (option) {
        this.sentFromSelect.sendKeys(option);
    }

    getSentFromSelect = function () {
        return this.sentFromSelect;
    }

    getSentFromSelectedOption = function () {
        return this.sentFromSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
