import { IMain } from 'app/entities/main/main.model';
import { ISub } from 'app/entities/sub/sub.model';
import { IUnSub } from 'app/entities/un-sub/un-sub.model';
import { ILink } from 'app/entities/link/link.model';
import { ILinkData } from 'app/entities/link-data/link-data.model';

export interface II18n {
  id?: number;
  fr?: string | null;
  en?: string | null;
  mainTitleFr?: IMain | null;
  mainTitleEn?: IMain | null;
  mainDescriptionFr?: IMain | null;
  mainDescriptionEn?: IMain | null;
  subTitleFr?: ISub | null;
  subTitleEn?: ISub | null;
  subDescriptionFr?: ISub | null;
  subDescriptionEn?: ISub | null;
  unSubTitleFr?: IUnSub | null;
  unSubTitleEn?: IUnSub | null;
  unSubDescriptionFr?: IUnSub | null;
  unSubDescriptionEn?: IUnSub | null;
  linkTitleFr?: ILink | null;
  linkTitleEn?: ILink | null;
  linkDescriptionFr?: ILink | null;
  linkDescriptionEn?: ILink | null;
  linkdataTitleFr?: ILinkData | null;
  linkdataTitleEn?: ILinkData | null;
  linkdataDescriptionFr?: ILinkData | null;
  linkdataDescriptionEn?: ILinkData | null;
  codeFr?: ILinkData | null;
  codeEn?: ILinkData | null;
}

export class I18n implements II18n {
  constructor(
    public id?: number,
    public fr?: string | null,
    public en?: string | null,
    public mainTitleFr?: IMain | null,
    public mainTitleEn?: IMain | null,
    public mainDescriptionFr?: IMain | null,
    public mainDescriptionEn?: IMain | null,
    public subTitleFr?: ISub | null,
    public subTitleEn?: ISub | null,
    public subDescriptionFr?: ISub | null,
    public subDescriptionEn?: ISub | null,
    public unSubTitleFr?: IUnSub | null,
    public unSubTitleEn?: IUnSub | null,
    public unSubDescriptionFr?: IUnSub | null,
    public unSubDescriptionEn?: IUnSub | null,
    public linkTitleFr?: ILink | null,
    public linkTitleEn?: ILink | null,
    public linkDescriptionFr?: ILink | null,
    public linkDescriptionEn?: ILink | null,
    public linkdataTitleFr?: ILinkData | null,
    public linkdataTitleEn?: ILinkData | null,
    public linkdataDescriptionFr?: ILinkData | null,
    public linkdataDescriptionEn?: ILinkData | null,
    public codeFr?: ILinkData | null,
    public codeEn?: ILinkData | null
  ) {}
}

export function getI18nIdentifier(i18n: II18n): number | undefined {
  return i18n.id;
}
