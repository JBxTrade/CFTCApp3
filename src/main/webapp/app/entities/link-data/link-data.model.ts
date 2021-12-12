import { II18n } from 'app/entities/i-18-n/i-18-n.model';
import { ISub } from 'app/entities/sub/sub.model';
import { IUnSub } from 'app/entities/un-sub/un-sub.model';
import { TheRole } from 'app/entities/enumerations/the-role.model';

export interface ILinkData {
  id?: number;
  theRole?: TheRole;
  imageCardContentType?: string;
  imageCard?: string;
  imageContentType?: string | null;
  image?: string | null;
  image2ContentType?: string | null;
  image2?: string | null;
  image3ContentType?: string | null;
  image3?: string | null;
  titleFr?: II18n | null;
  titleEn?: II18n | null;
  descriptionFr?: II18n | null;
  descriptionEn?: II18n | null;
  codeFr?: II18n | null;
  codeEn?: II18n | null;
  sub?: ISub | null;
  unSub?: IUnSub | null;
}

export class LinkData implements ILinkData {
  constructor(
    public id?: number,
    public theRole?: TheRole,
    public imageCardContentType?: string,
    public imageCard?: string,
    public imageContentType?: string | null,
    public image?: string | null,
    public image2ContentType?: string | null,
    public image2?: string | null,
    public image3ContentType?: string | null,
    public image3?: string | null,
    public titleFr?: II18n | null,
    public titleEn?: II18n | null,
    public descriptionFr?: II18n | null,
    public descriptionEn?: II18n | null,
    public codeFr?: II18n | null,
    public codeEn?: II18n | null,
    public sub?: ISub | null,
    public unSub?: IUnSub | null
  ) {}
}

export function getLinkDataIdentifier(linkData: ILinkData): number | undefined {
  return linkData.id;
}
