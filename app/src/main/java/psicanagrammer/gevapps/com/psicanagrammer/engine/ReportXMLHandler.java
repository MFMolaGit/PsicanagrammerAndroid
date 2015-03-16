package psicanagrammer.gevapps.com.psicanagrammer.engine;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import psicanagrammer.gevapps.com.psicanagrammer.dto.Group;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Phase;
import psicanagrammer.gevapps.com.psicanagrammer.dto.QuestionResults;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Record;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Report;
import psicanagrammer.gevapps.com.psicanagrammer.dto.State;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

/**
 * Created by Geva on 14/03/2015.
 */
public class ReportXMLHandler extends DefaultHandler {

    private StringBuilder value;
    private Report report;
    private Group group;
    private List<Phase> phases;
    private Phase phase;
    private List<Record> records;
    private Record record;
    private QuestionResults questionResults;
    private boolean inReport, inGroup, inPhases, inPhase, inLatency, inQuestions, inQuestion, inRecords, inRecord;
    private boolean inOverResponses, inOverWords, inCorrects, inFails, inTimeouts, inTraining;
    private String question;
    private Integer response;

    @Override
    public void startDocument() throws SAXException {
        value = new StringBuilder();
    }

    @Override
    public void startElement (String uri, String localName, String qName, Attributes attributes) throws SAXException {
        value.setLength(0);
        if(localName.equals("informe")) {
            report = new Report();
            inReport = true;
        } else if(localName.equals("grupo")) {
            inGroup = true;
            group = new Group();
        } if(localName.equals("sobreRespuestas")) {
            inOverResponses = true;
        } if(localName.equals("sobrePalabras")) {
            inOverWords = true;
        } else if(localName.equals("aciertos")) {
            inCorrects = true;
        } else if(localName.equals("fallos")) {
            inFails = true;
        } else if(localName.equals("fueraTiempo")) {
            inTimeouts = true;
        } else if(localName.equals("latencia")) {
            inLatency = true;
        } else if(localName.equals("ensayoCriterio")) {
            inTraining = true;
        } else if(localName.equals("fases")) {
            inPhases = true;
            phases = new ArrayList<>();
        } else if(localName.equals("fase")) {
            inPhase = true;
            phase = new Phase();
        } else if(localName.equals("registros")) {
            inRecords = true;
            records = new ArrayList<>();
        } else if(localName.equals("registro")) {
            inRecord = true;
            record = new Record();
        } else if(localName.equals("preguntas")) {
            inQuestions = true;
            questionResults = new QuestionResults();
        } else if(localName.equals("pregunta")) {
            inQuestion = true;
        }
    }

    @Override
    public void characters( char ch[], int begin, int length) throws SAXException {
        value.append(ch, begin, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(inReport) {
            if(localName.equals("grupo")) {
                inGroup = false;
                report.setGroup(group);
            } else if(localName.equals("pregunta")) {
                inQuestion = false;
                questionResults.addResponse(question,response);
            } else if(localName.equals("preguntas")) {
                inQuestions = false;
                report.setResultQuestions(questionResults);
            } else if (localName.equals("informe")) {
                inReport = false;
            } else if(inQuestion) {
                if(localName.equals("enunciado")) {
                    question = value.toString();
                } else if(localName.equals("respuesta")) {
                    response = Integer.valueOf(value.toString());
                }
            } else if (inGroup) {
                if (localName.equals("fases")) {
                    inPhases = false;
                    group.setRegisterList(phases);
                } else if (inPhase) {
                    if (localName.equals("fase")) {
                        inPhase = false;
                        phases.add(phase);
                    } else if (localName.equals("registros")) {
                        inRecords = false;
                        phase.setRegisterList(records);
                    } else if (inRecord) {
                        if (localName.equals("fecha")) {
                            try {
                                record.setTimestamp(Constants.SIMPLE_DATE_FORMAT.parse(value.toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else if (localName.equals("anagrama")) {
                            record.setAnagram(value.toString());
                        } else if (localName.equals("solucion")) {
                            record.setSolution(value.toString());
                        } else if (localName.equals("respuesta")) {
                            record.setResponse(value.toString());
                        } else if (localName.equals("tiempo")) {
                            record.setSeconds(Integer.valueOf(value.toString()));
                        } else if (localName.equals("estado")) {
                            String sState = value.toString();

                            if (sState.equals("CORRECTO")) {
                                record.setState(State.OK);
                            } else if (sState.equals("INCORRECTO")) {
                                record.setState(State.KO);
                            } else if (sState.equals("FUERA DE TIEMPO")) {
                                record.setState(State.TIMEOUT);
                            }
                        } else if (localName.equals("registro")) {
                            inRecord = false;
                            records.add(record);
                        }
                    } else if (inOverResponses) {
                        if (localName.equals("total")) {
                            phase.setTotal(Integer.valueOf(value.toString()));
                        } else if (inCorrects) {
                            if (localName.equals("valor")) {
                                phase.setCorrectsCount(Integer.valueOf(value.toString()));
                            } else if (localName.equals("porcentaje")) {
                                phase.setOkPercent(Float.valueOf(value.toString()));
                            } else if (localName.equals("aciertos")) {
                                inCorrects = false;
                            }
                        } else if (inFails) {
                            if (localName.equals("valor")) {
                                phase.setFailsCount(Integer.valueOf(value.toString()));
                            } else if (localName.equals("porcentaje")) {
                                phase.setKoPercent(Float.valueOf(value.toString()));
                            } else if (localName.equals("fallos")) {
                                inFails = false;
                            }
                        } else if (inTimeouts) {
                            if (localName.equals("valor")) {
                                phase.setTimeoutsCount(Integer.valueOf(value.toString()));
                            } else if (localName.equals("porcentaje")) {
                                phase.setToPercent(Float.valueOf(value.toString()));
                            } else if (localName.equals("fueraTiempo")) {
                                inTimeouts = false;
                            }
                        } else if (localName.equals("sobreRespuestas")) {
                            inOverResponses = false;
                        }
                    } else if (inOverWords) {
                        if (localName.equals("total")) {
                            phase.setTotalLoaded(Integer.valueOf(value.toString()));
                        } else if (inCorrects) {
                            if (localName.equals("valor")) {
                                phase.setCorrectsCount(Integer.valueOf(value.toString()));
                            } else if (localName.equals("porcentaje")) {
                                phase.setOk2Percent(Float.valueOf(value.toString()));
                            } else if (localName.equals("aciertos")) {
                                inCorrects = false;
                            }
                        } else if (inTimeouts) {
                            if (localName.equals("valor")) {
                                phase.setTimeoutsCount(Integer.valueOf(value.toString()));
                            } else if (localName.equals("porcentaje")) {
                                phase.setTo2Percent(Float.valueOf(value.toString()));
                            } else if (localName.equals("fueraTiempo")) {
                                inTimeouts = false;
                            }
                        } else if (localName.equals("sobrePalabras")) {
                            inOverWords = false;
                        }
                    } else if (inLatency) {
                        if (localName.equals("correcta")) {
                            phase.setCorrectResponseLatency(Float.valueOf(value.toString()));
                        } else if (localName.equals("general")) {
                            phase.setResponseLatency(Float.valueOf(value.toString()));
                        } else if (localName.equals("latencia")) {
                            inLatency = false;
                        }
                    } else if (inTraining) {
                        if (localName.equals("veces")) {
                            //Constant
                        } else if (localName.equals("tiempo")) {
                            //Constant
                        } else if (localName.equals("ratio")) {
                            phase.setCriterialTraining(Integer.valueOf(value.toString()));
                        } else if (localName.equals("ensayoCriterio")) {
                            inTraining = false;
                        }
                    } else if (localName.equals("nombre")) {
                        phase.setName(value.toString());
                    } else if (localName.equals("tiempo")) {
                        phase.setSeconds(Integer.valueOf(value.toString()));
                    }
                } else if (inOverResponses) {
                    if (localName.equals("total")) {
                        group.setTotal(Integer.valueOf(value.toString()));
                    } else if (inCorrects) {
                        if (localName.equals("valor")) {
                            group.setCorrectsCount(Integer.valueOf(value.toString()));
                        } else if (localName.equals("porcentaje")) {
                            group.setOkPercent(Float.valueOf(value.toString()));
                        } else if (localName.equals("aciertos")) {
                            inCorrects = false;
                        }
                    } else if (inFails) {
                        if (localName.equals("valor")) {
                            group.setFailsCount(Integer.valueOf(value.toString()));
                        } else if (localName.equals("porcentaje")) {
                            group.setKoPercent(Float.valueOf(value.toString()));
                        } else if (localName.equals("fallos")) {
                            inFails = false;
                        }
                    } else if (inTimeouts) {
                        if (localName.equals("valor")) {
                            group.setTimeoutsCount(Integer.valueOf(value.toString()));
                        } else if (localName.equals("porcentaje")) {
                            group.setToPercent(Float.valueOf(value.toString()));
                        } else if (localName.equals("fueraTiempo")) {
                            inTimeouts = false;
                        }
                    } else if (localName.equals("sobreRespuestas")) {
                        inOverResponses = false;
                    }
                } else if (inOverWords) {
                    if (localName.equals("total")) {
                        group.setTotalLoaded(Integer.valueOf(value.toString()));
                    } else if (inCorrects) {
                        if (localName.equals("valor")) {
                            group.setCorrectsCount(Integer.valueOf(value.toString()));
                        } else if (localName.equals("porcentaje")) {
                            group.setOk2Percent(Float.valueOf(value.toString()));
                        } else if (localName.equals("aciertos")) {
                            inCorrects = false;
                        }
                    } else if (inTimeouts) {
                        if (localName.equals("valor")) {
                            group.setTimeoutsCount(Integer.valueOf(value.toString()));
                        } else if (localName.equals("porcentaje")) {
                            group.setTo2Percent(Float.valueOf(value.toString()));
                        } else if (localName.equals("fueraTiempo")) {
                            inTimeouts = false;
                        }
                    } else if (localName.equals("sobrePalabras")) {
                        inOverWords = false;
                    }
                } else if (inLatency) {
                    if (localName.equals("correcta")) {
                        group.setCorrectResponseLatency(Float.valueOf(value.toString()));
                    } else if (localName.equals("general")) {
                        group.setResponseLatency(Float.valueOf(value.toString()));
                    } else if (localName.equals("latencia")) {
                        inLatency = false;
                    }
                } else if (inTraining) {
                    if (localName.equals("veces")) {
                        //Constant
                    } else if (localName.equals("tiempo")) {
                        //Constant
                    } else if (localName.equals("ratio")) {
                        group.setGeneralCriterialTraining(Integer.valueOf(value.toString()));
                    } else if (localName.equals("ensayoCriterio")) {
                        inTraining = false;
                    }
                } else if (localName.equals("nombre")) {
                    group.setName(value.toString());
                } else if (localName.equals("tiempo")) {
                    group.setSeconds(Integer.valueOf(value.toString()));
                }
            } else if (localName.equals("nombre")) {
                report.setFileReportName(value.toString());
            } else if (localName.equals("fecha")) {
                try {
                    report.setTimestamp(Constants.SIMPLE_DATE_FORMAT.parse(value.toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void endDocument () throws SAXException {
        System.out.println("Informe cargado");
    }

    public Report getReport() {
        return report;
    }
}
