import { II18n } from 'app/entities/i-18-n/i-18-n.model';
import { ILink } from 'app/entities/link/link.model';
import { ILinkData } from 'app/entities/link-data/link-data.model';
import { IUnSub } from 'app/entities/un-sub/un-sub.model';
import { TheRole } from 'app/entities/enumerations/the-role.model';

export interface ISub {
  id?: number;
  theRole?: TheRole;
  imageContentType?: string;
  image?: string;
  titleFr?: II18n | null;
  titleEn?: II18n | null;
  descriptionFr?: II18n | null;
  descriptionEn?: II18n | null;
  links?: ILink[] | null;
  linkData?: ILinkData[] | null;
  unSub?: IUnSub | null;
}

export class Sub implements ISub {
  constructor(
    public id?: number,
    public theRole?: TheRole,
    public imageContentType?: string,
    public image?: string,
    public titleFr?: II18n | null,
    public titleEn?: II18n | null,
    public descriptionFr?: II18n | null,
    public descriptionEn?: II18n | null,
    public links?: ILink[] | null,
    public linkData?: ILinkData[] | null,
    public unSub?: IUnSub | null
  ) {}
}

export function getSubIdentifier(sub: ISub): number | undefined {
  return sub.id;
}
